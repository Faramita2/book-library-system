package app.website;

import app.api.website.UserAJAXWebService;
import app.website.user.service.UserService;
import app.website.web.interceptor.AuthInterceptor;
import app.website.web.UserAJAXWebServiceImpl;
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
        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(UserService.class);
    }
}
