package app.user.user.service;

import app.user.api.user.GetUserByUsernameRequest;
import app.user.api.user.GetUserByUsernameResponse;
import app.user.api.user.GetUserResponse;
import app.user.api.user.ResetUserPasswordRequest;
import app.user.user.exception.UserException;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.log.Markers;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * @author zoo
 */
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    Repository<User> userRepository;
    String secretKey;

    public UserService(String secretKey) {
        this.secretKey = secretKey;
    }

    public GetUserResponse get(Long id) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));

        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }

    public GetUserByUsernameResponse getUserByUsername(GetUserByUsernameRequest request) {
        User user = userRepository.selectOne("username = ?", request.username).orElseThrow(() ->
            new BadRequestException(Strings.format("user not exists, username = {}", request.username), "USER_NOT_FOUND"));

        GetUserByUsernameResponse response = new GetUserByUsernameResponse();
        response.id = user.id;
        response.email = user.email;
        response.salt = user.salt;
        response.password = user.password;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }

    public void resetPassword(Long id, ResetUserPasswordRequest request) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));
        user.updatedBy = request.requestedBy;
        user.updatedTime = LocalDateTime.now();
        try {
            hashPassword(user, request.password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(Markers.errorCode("HASH_PASSWORD_ERROR"), e.getMessage());
            throw new UserException("reset password failed.", e, "HASH_PASSWORD_ERROR");
        }

        userRepository.partialUpdate(user);
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        return salt;
    }

    private void hashPassword(User user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance(secretKey);
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        user.password = enc.encodeToString(hash);
        user.salt = enc.encodeToString(salt);
    }
}
