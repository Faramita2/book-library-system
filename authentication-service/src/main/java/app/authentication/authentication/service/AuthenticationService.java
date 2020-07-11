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
    @Inject
    UserWebService userWebService;
    @Inject
    Redis redis;
    private final String secretKey;

    public AuthenticationService(String secretKey) {
        this.secretKey = secretKey;
    }

    public LoginResponse login(LoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest();
        getUserByUsernameRequest.username = request.username;
        GetUserByUsernameResponse getUserByUsernameResponse = userWebService.getUserByUsername(getUserByUsernameRequest);

        if (getUserByUsernameResponse.status == UserStatusView.INACTIVE) {
            throw new BadRequestException("user not active", "USER_INACTIVE");
        }

        if (!getUserByUsernameResponse.password.equals(getPasswordHash(request.password, getUserByUsernameResponse.salt))) {
            throw new BadRequestException("username or password incorrect", "USER_PASSWORD_INCORRECT");
        }

        LoginResponse response = new LoginResponse();
        response.id = getUserByUsernameResponse.id;
        response.email = getUserByUsernameResponse.email;
        response.status = LoginResponse.UserStatusView.valueOf(getUserByUsernameResponse.status.name());

        return response;
    }

    public void resetPassword(ResetPasswordRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String userId = redis.get(request.token);
        if (Strings.isBlank(userId)) {
            throw new BadRequestException("token expired", "RESET_PASSWORD_TOKEN_EXPIRED");
        }

        ResetUserPasswordRequest resetUserPasswordRequest = new ResetUserPasswordRequest();
        resetUserPasswordRequest.password = request.password;
        resetUserPasswordRequest.requestedBy = request.requestedBy;

        userWebService.resetPassword(Long.valueOf(userId), resetUserPasswordRequest);
    }

    private String getPasswordHash(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String passwordHash = "";
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(secretKey);
        byte[] encoded = secretKeyFactory.generateSecret(spec).getEncoded();
        passwordHash = Base64.getEncoder().encodeToString(encoded);

        return passwordHash;
    }
}
