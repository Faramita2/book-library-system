package app.authentication.authentication.web;

import app.api.authentication.AuthenticationWebService;
import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import app.authentication.authentication.service.AuthenticationService;
import core.framework.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author zoo
 */
public class AuthenticationWebServiceImpl implements AuthenticationWebService {
    @Inject
    AuthenticationService service;

    @Override
    public LoginResponse login(LoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return service.login(request);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        service.resetPassword(request);
    }
}
