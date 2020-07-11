package app.website.service;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.website.api.user.LoginAJAXRequest;
import app.website.api.user.ResetPasswordAJAXRequest;
import app.website.web.WebsiteError;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author meow
 */
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    AuthenticationWebService authenticationWebService;
    @Inject
    Redis redis;

    public LoginResponse login(LoginAJAXRequest request, String sessionId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = request.username;
        loginRequest.password = request.password;
        logger.info(sessionId);
        LoginResponse loginResponse;
        try {
            loginResponse = authenticationWebService.login(loginRequest);
            redis.hash().set(Strings.format("session:{}", sessionId), "status", loginResponse.status.name());
            redis.set().add(Strings.format("users:{}:sessionIds", loginResponse.id), sessionId);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new WebsiteError("login failed.", e);
        }

        return loginResponse;
    }

    public void resetPassword(ResetPasswordAJAXRequest request) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.password = request.password;
        resetPasswordRequest.token = request.token;
        resetPasswordRequest.requestedBy = request.requestedBy;

        try {
            authenticationWebService.resetPassword(resetPasswordRequest);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new WebsiteError("reset password failed.", e);
        }
        String userId = redis.get(request.token);
        redis.set().members(Strings.format("users:{}:sessionIds", userId)).stream().map(s -> Strings.format("session:{}", s)).forEach(redis::del);
        redis.del(Strings.format("users:{}:sessionIds", userId));
        redis.del(request.token);
        redis.del(userId);
    }
}
