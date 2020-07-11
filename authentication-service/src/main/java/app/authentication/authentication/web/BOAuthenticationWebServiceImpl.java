package app.authentication.authentication.web;

import app.api.authentication.BOAuthenticationWebService;
import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import app.authentication.authentication.service.BOAuthenticationService;
import core.framework.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author zoo
 */
public class BOAuthenticationWebServiceImpl implements BOAuthenticationWebService {
    @Inject
    BOAuthenticationService service;

    @Override
    public BOLoginResponse login(BOLoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return service.login(request);
    }
}
