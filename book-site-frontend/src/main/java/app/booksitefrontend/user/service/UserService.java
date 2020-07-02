package app.booksitefrontend.user.service;

import app.api.booksitefrontend.user.LoginUserAJAXRequest;
import app.user.api.UserWebService;
import app.user.api.user.LoginUserRequest;
import app.user.api.user.LoginUserResponse;
import core.framework.inject.Inject;
import core.framework.web.Session;
import core.framework.web.WebContext;

/**
 * @author meow
 */
public class UserService {
    @Inject
    UserWebService userWebService;
    @Inject
    WebContext webContext;

    public void login(LoginUserAJAXRequest request) {
        LoginUserRequest req = new LoginUserRequest();
        req.username = request.username;
        req.password = request.password;
        LoginUserResponse resp = userWebService.login(req);
        Session session = webContext.request().session();
        session.set("user_id", String.valueOf(resp.id));
        session.set("username", resp.username);
        session.set("email", resp.email);
        session.set("user_status", resp.status.name());
    }
}
