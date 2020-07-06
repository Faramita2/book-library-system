package app.website.user.web;

import app.api.website.UserAJAXWebService;
import app.api.website.user.LoginUserAJAXRequest;
import app.website.user.service.UserService;
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
