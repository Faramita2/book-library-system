package app.booksite;

import app.api.booksite.UserAJAXWebService;
import app.booksite.user.service.UserService;
import app.booksite.user.web.UserAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        bind(UserService.class);
        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));
    }
}
