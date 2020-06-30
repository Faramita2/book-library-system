package app.booksite.user.service;

import app.api.booksite.user.CreateUserAJAXRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class UserService {
    @Inject
    BOUserWebService userWebService;

    public void create(CreateUserAJAXRequest request) {
        BOCreateUserRequest req = new BOCreateUserRequest();
        req.email = request.email;
        req.username = request.username;
        req.password = request.password;
        req.status = UserStatusView.valueOf(request.status.name());
        req.operator = "book-site";
        userWebService.create(req);
    }
}
