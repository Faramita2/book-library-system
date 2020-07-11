package app.api.authentication;

import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author zoo
 */
public interface AuthenticationWebService {
    @PUT
    @Path("/login")
    LoginResponse login(LoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException;

    @PUT
    @Path("/reset-password")
    void resetPassword(ResetPasswordRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
