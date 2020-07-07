package app.backoffice;

import app.api.backoffice.UserAJAXWebService;
import app.backoffice.service.UserService;
import app.backoffice.api.UserAJAXWebServiceImpl;
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
