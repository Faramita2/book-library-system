package app.booksite.admin.service;

import app.api.authentication.BOAuthenticationWebService;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import app.api.backoffice.admin.LoginAJAXRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

/**
 * @author zoo
 */
public class AdminService {
    @Inject
    BOAuthenticationWebService boAuthenticationWebService;
    @Inject
    WebContext webContext;

    public void login(LoginAJAXRequest request) {
        BOLoginRequest boLoginRequest = new BOLoginRequest();
        boLoginRequest.account = request.account;
        boLoginRequest.password = request.password;
        BOLoginResponse boLoginResponse = boAuthenticationWebService.login(boLoginRequest);

        webContext.request().session().set("admin_id", String.valueOf(boLoginResponse.id));
        webContext.request().session().set("admin_account", boLoginResponse.account);
    }
}
