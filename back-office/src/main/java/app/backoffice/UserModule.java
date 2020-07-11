package app.backoffice;

import app.api.backoffice.UserAJAXWebService;
import app.backoffice.api.UserAJAXWebServiceImpl;
import app.backoffice.service.UserService;
import core.framework.module.Module;
import core.framework.web.exception.NotFoundException;

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
        String emailHostName = property("app.emailHostName").orElseThrow(() -> new NotFoundException("not email hostname!"));
        UserAJAXWebServiceImpl userAJAXWebServiceImpl = new UserAJAXWebServiceImpl(emailHostName);
        api().service(UserAJAXWebService.class, bind(userAJAXWebServiceImpl));
    }
}
