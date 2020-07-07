package app.website.user.service;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.Session;
import core.framework.web.WebContext;

/**
 * @author meow
 */
public class UserService {
    @Inject
    AuthenticationWebService authenticationWebService;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

    public void login(LoginAJAXRequest request) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = request.username;
        loginRequest.password = request.password;
        LoginResponse loginResponse = authenticationWebService.login(loginRequest);

        Session session = webContext.request().session();
        session.set("user_id", String.valueOf(loginResponse.id));
        session.set("username", request.username);
        session.set("email", loginResponse.email);
        session.set("user_status", loginResponse.status.name());

        redis.set(Strings.format("users:{}:status", loginResponse.id), loginResponse.status.name());
    }

    public void resetPassword(ResetPasswordAJAXRequest request) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.password = request.password;
        resetPasswordRequest.token = request.token;
        resetPasswordRequest.requestedBy = request.requestedBy;

        authenticationWebService.resetPassword(resetPasswordRequest);
    }

    public void logout() {
        Session session = webContext.request().session();
        String userId = session.get("user_id").orElse(null);
        redis.del(Strings.format("users:{}:status", userId));
        session.invalidate();
    }
}
