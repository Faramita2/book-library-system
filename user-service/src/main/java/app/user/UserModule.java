package app.user;

import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import app.user.user.domain.User;
import app.user.user.service.BOUserService;
import app.user.user.service.UserService;
import app.user.user.web.BOUserWebServiceImpl;
import app.user.user.web.UserWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BOUserWebService.class, bind(BOUserWebServiceImpl.class));
        api().service(UserWebService.class, bind(UserWebServiceImpl.class));
    }

    private void services() {
        String secretKey = property("app.secretKey").orElseThrow();
        BOUserService boUserService = new BOUserService(secretKey);
        bind(boUserService);
        UserService userService = new UserService(secretKey);
        bind(userService);
    }

    private void dbs() {
        db().repository(User.class);
    }
}
