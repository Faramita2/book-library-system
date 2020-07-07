package app.website.api;

import app.api.website.UserAJAXWebService;
import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import app.website.service.UserService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;

    @SkipLogin
    @Override
    public void login(LoginAJAXRequest request) {
        service.login(request);
    }

    @Override
    public void logout() {
        service.logout();
    }

    @Override
    public void resetPassword(ResetPasswordAJAXRequest request) {
        service.resetPassword(request);
    }
}
