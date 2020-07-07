package app.authentication.authentication.service;

import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.user.api.UserWebService;
import app.user.api.user.GetUserByUsernameRequest;
import app.user.api.user.GetUserByUsernameResponse;
import app.user.api.user.ResetUserPasswordRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
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
public class AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Inject
    UserWebService userWebService;
    @Inject
    Redis redis;

    public LoginResponse login(LoginRequest request) {
        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest();
        GetUserByUsernameResponse getUserByUsernameResponse = userWebService.getUserByUsername(getUserByUsernameRequest);

        if (getUserByUsernameResponse.status == UserStatusView.INACTIVE) {
            throw new BadRequestException("user not active", "USER_INACTIVE");
        }

        if (!getUserByUsernameResponse.password.equals(getPasswordHash(request.password, getUserByUsernameResponse.salt))) {
            throw new BadRequestException("password incorrect", "USER_PASSWORD_INCORRECT");
        }

        LoginResponse response = new LoginResponse();
        response.id = getUserByUsernameResponse.id;
        response.username = getUserByUsernameResponse.username;
        response.email = getUserByUsernameResponse.email;
        response.status = LoginResponse.UserStatusView.valueOf(getUserByUsernameResponse.status.name());

        return response;
    }

    public void resetPassword(ResetPasswordRequest request) {
        String userId = redis.get(request.token);
        if (Strings.isBlank(userId)) {
            throw new BadRequestException("token expired", "RESET_PASSWORD_TOKEN_EXPIRED");
        }

        ResetUserPasswordRequest resetUserPasswordRequest = new ResetUserPasswordRequest();
        resetUserPasswordRequest.password = request.password;
        resetUserPasswordRequest.requestedBy = request.requestedBy;

        userWebService.resetPassword(Long.parseLong(userId), resetUserPasswordRequest);
    }

    private String getPasswordHash(String password, String salt) {
        String passwordHash = "";
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] encoded = secretKeyFactory.generateSecret(spec).getEncoded();
            passwordHash = Base64.getEncoder().encodeToString(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.info("hash password error, message = {}", e.getMessage());
        }

        return passwordHash;
    }
}
