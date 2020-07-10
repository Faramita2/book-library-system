package app.website.api;

import app.website.api.user.LoginAJAXRequest;
import app.website.api.user.ResetPasswordAJAXRequest;
import core.framework.api.web.service.POST;
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

    @POST
    @Path("/ajax/reset-password")
    void resetPassword(ResetPasswordAJAXRequest request);
}
