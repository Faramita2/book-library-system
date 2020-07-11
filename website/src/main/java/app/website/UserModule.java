package app.website;

import app.website.api.UserAJAXWebService;
import app.website.service.UserService;
import app.website.web.interceptor.AuthInterceptor;
import app.website.api.UserAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        bind(UserService.class);
        apiServices();
    }

    private void apiServices() {
        http().intercept(bind(AuthInterceptor.class));
        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
    }
}
