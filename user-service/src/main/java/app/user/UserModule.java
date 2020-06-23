package app.user;

import app.user.api.BOUserWebService;
import app.user.user.domain.User;
import app.user.user.service.BOUserService;
import app.user.user.web.BOUserWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        db().repository(User.class);
        bind(BOUserService.class);
        api().service(BOUserWebService.class, bind(BOUserWebServiceImpl.class));
    }
}
