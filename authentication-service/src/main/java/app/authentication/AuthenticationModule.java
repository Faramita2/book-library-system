package app.authentication;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.BOAuthenticationWebService;
import app.authentication.authentication.service.AuthenticationService;
import app.authentication.authentication.service.BOAuthenticationService;
import app.authentication.authentication.web.AuthenticationWebServiceImpl;
import app.authentication.authentication.web.BOAuthenticationWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AuthenticationModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(AuthenticationWebService.class, bind(AuthenticationWebServiceImpl.class));
        api().service(BOAuthenticationWebService.class, bind(BOAuthenticationWebServiceImpl.class));
    }

    private void services() {
        String secretKey = property("app.secretKey").orElseThrow();
        AuthenticationService authenticationService = new AuthenticationService(secretKey);
        bind(authenticationService);
        BOAuthenticationService boAuthenticationService = new BOAuthenticationService(secretKey);
        bind(boAuthenticationService);
    }
}
