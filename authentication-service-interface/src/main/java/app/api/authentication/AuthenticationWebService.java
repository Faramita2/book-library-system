package app.api.authentication;

import app.api.authentication.authentication.LoginRequest;
import app.api.authentication.authentication.LoginResponse;
import app.api.authentication.authentication.ResetPasswordRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface AuthenticationWebService {
    @PUT
    @Path("/authentication/login")
    LoginResponse login(LoginRequest request);

    @PUT
    @Path("/authentication/reset-password")
    void resetPassword(ResetPasswordRequest request);
}
