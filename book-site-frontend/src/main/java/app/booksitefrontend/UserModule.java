package app.booksitefrontend;

import app.api.booksitefrontend.UserAJAXWebService;
import app.booksitefrontend.user.service.UserService;
import app.booksitefrontend.user.web.AuthInterceptor;
import app.booksitefrontend.user.web.UserAJAXWebServiceImpl;
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
