package app.website.service;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Maps;
import core.framework.util.Strings;

import java.util.Map;

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

        Map<String, String> user = Maps.newHashMap();
        user.put("status", loginResponse.status.name());
        user.put("login", String.valueOf(true));
        redis.hash().multiSet(Strings.format("users:{}", loginResponse.id), user);

        return loginResponse;
    }

    public void resetPassword(ResetPasswordAJAXRequest request) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.password = request.password;
        resetPasswordRequest.token = request.token;
        resetPasswordRequest.requestedBy = request.requestedBy;

        authenticationWebService.resetPassword(resetPasswordRequest);
        logout(redis.get(request.token));
        redis.del(request.token);
    }

    public void logout(String userId) {
        redis.hash().del(Strings.format("users:{}", userId), "status", "login");
    }
}
