package app.booksite;

import app.api.backoffice.UserAJAXWebService;
import app.booksite.service.UserService;
import app.booksite.api.UserAJAXWebServiceImpl;
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
        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(UserService.class);
    }
}
