package app.backoffice.service;

import app.api.authentication.BOAuthenticationWebService;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import app.api.backoffice.admin.LoginAJAXRequest;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class AdminService {
    @Inject
    BOAuthenticationWebService boAuthenticationWebService;

    public BOLoginResponse login(LoginAJAXRequest request) {
        BOLoginRequest boLoginRequest = new BOLoginRequest();
        boLoginRequest.account = request.account;
        boLoginRequest.password = request.password;

        return boAuthenticationWebService.login(boLoginRequest);
    }
}
