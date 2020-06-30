package app.booksitefrontend.user.web;

import app.api.booksitefrontend.UserAJAXWebService;
import app.api.booksitefrontend.user.LoginUserAJAXRequest;
import app.booksitefrontend.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;

    @UserPass
    @Override
    public void login(LoginUserAJAXRequest request) {
        service.login(request);
    }
}
