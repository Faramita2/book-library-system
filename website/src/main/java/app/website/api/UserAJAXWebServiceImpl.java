package app.website.api;

import app.api.authentication.authentication.LoginResponse;
import app.api.website.UserAJAXWebService;
import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import app.website.service.UserService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;
import core.framework.web.Session;
import core.framework.web.WebContext;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;
    @Inject
    WebContext webContext;

    @SkipLogin
    @Override
    public void login(LoginAJAXRequest request) {
        LoginResponse loginResponse = service.login(request);

        Session session = webContext.request().session();
        session.set("user_id", String.valueOf(loginResponse.id));
        session.set("username", request.username);
        session.set("email", loginResponse.email);
        session.set("user_status", loginResponse.status.name());
    }

    @Override
    public void logout() {
        String userId = webContext.request().session().get("user_id").orElse(null);
        service.logout(userId);
        webContext.request().session().invalidate();
    }

    @Override
    public void resetPassword(ResetPasswordAJAXRequest request) {
        service.resetPassword(request);
    }
}
