package app.api.website;

import app.api.website.user.LoginAJAXRequest;
import app.api.website.user.ResetPasswordAJAXRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface UserAJAXWebService {
    @PUT
    @Path("/ajax/login")
    void login(LoginAJAXRequest request);

    @PUT
    @Path("/ajax/logout")
    void logout();

    @PUT
    @Path("/ajax/reset-password")
    void resetPassword(ResetPasswordAJAXRequest request);
}
