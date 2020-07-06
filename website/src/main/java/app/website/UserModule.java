package app.website;

import app.api.website.UserAJAXWebService;
import app.website.user.service.AuthenticationService;
import app.website.user.web.AuthInterceptor;
import app.website.user.web.AuthenticationAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        http().intercept(bind(AuthInterceptor.class));
        api().service(UserAJAXWebService.class, bind(AuthenticationAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(AuthenticationService.class);
    }
}
