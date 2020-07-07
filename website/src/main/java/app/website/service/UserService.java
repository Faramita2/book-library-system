package app.website.service;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;

/**
 * @author meow
 */
public class UserService {
    @Inject
    AuthenticationWebService authenticationWebService;
    @Inject
    Redis redis;

    public LoginResponse login(LoginAJAXRequest request) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = request.username;
        loginRequest.password = request.password;
        LoginResponse loginResponse = authenticationWebService.login(loginRequest);

        redis.set(Strings.format("users:{}:status", loginResponse.id), loginResponse.status.name());
        redis.set(Strings.format("users:{}:login", loginResponse.id), "TRUE");

        return loginResponse;
    }

    public void resetPassword(ResetPasswordAJAXRequest request) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.password = request.password;
        resetPasswordRequest.token = request.token;
        resetPasswordRequest.requestedBy = request.requestedBy;

        authenticationWebService.resetPassword(resetPasswordRequest);
        String userId = redis.get(request.token);
        redis.del(Strings.format("users:{}:login", userId));
        redis.del(request.token);
    }

    public void logout(String userId) {
        redis.del(Strings.format("users:{}:status", userId));
    }
}
