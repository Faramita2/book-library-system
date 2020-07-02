package app.user.user.service;

import app.user.api.user.GetUserResponse;
import app.user.api.user.LoginUserRequest;
import app.user.api.user.LoginUserResponse;
import app.user.api.user.UserStatusView;
import app.user.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author zoo
 */
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    Repository<User> repository;

    public GetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("user not found, id = {}", id), "USER_NOT_FOUND"));

        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.username = user.username;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());

        return response;
    }

    public LoginUserResponse login(LoginUserRequest request) {
        User user = repository.selectOne("username = ?", request.username).orElseThrow(() ->
            new BadRequestException("username or password incorrect", "USER_PASSWORD_INCORRECT"));


        if (user.password.equals(getPasswordHash(request, user.salt))) {
            LoginUserResponse response = new LoginUserResponse();
            response.id = user.id;
            response.username = user.username;
            response.email = user.email;
            return response;
        }

        throw new BadRequestException("username or password incorrect", "USER_PASSWORD_INCORRECT");
    }

    private String getPasswordHash(LoginUserRequest request, String salt) {
        String passwordHash = "";
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(request.password.toCharArray(), saltBytes, 65536, 128);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] encoded = secretKeyFactory.generateSecret(spec).getEncoded();
            passwordHash = Base64.getEncoder().encodeToString(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("hash password error, message = {}", e.getMessage());
        }

        return passwordHash;
    }
}
