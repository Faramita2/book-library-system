package app.user.api;

import app.user.api.user.ResetUserPasswordRequest;
import app.user.api.user.GetUserByUsernameRequest;
import app.user.api.user.GetUserByUsernameResponse;
import app.user.api.user.GetUserResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author zoo
 */
public interface UserWebService {
    @GET
    @Path("/user/:id")
    GetUserResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/user")
    GetUserByUsernameResponse getUserByUsername(GetUserByUsernameRequest request);

    @PUT
    @Path("/user/:id/reset-password")
    void resetPassword(@PathParam("id") Long id, ResetUserPasswordRequest request);
}
