package app.backoffice.service;

import app.api.authentication.BOAuthenticationWebService;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import app.api.backoffice.admin.LoginAJAXRequest;
import app.backoffice.web.BackOfficeException;
import core.framework.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

        try {
            return boAuthenticationWebService.login(boLoginRequest);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new BackOfficeException("login failed.", e);
        }
    }
}
