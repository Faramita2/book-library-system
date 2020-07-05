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
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.username = request.username;
        loginUserRequest.password = request.password;
        LoginUserResponse loginUserResponse = userWebService.login(loginUserRequest);
        Session session = webContext.request().session();
        // todo logout
        session.set("user_id", String.valueOf(loginUserResponse.id));
        session.set("username", loginUserResponse.username);
        session.set("email", loginUserResponse.email);
        session.set("user_status", loginUserResponse.status.name());
    }
}
