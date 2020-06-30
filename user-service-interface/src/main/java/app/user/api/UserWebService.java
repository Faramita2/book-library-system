package app.user.api;

import app.user.api.user.GetUserResponse;
import app.user.api.user.LoginUserRequest;
import app.user.api.user.LoginUserResponse;
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
    @Path("/user/login")
    LoginUserResponse login(LoginUserRequest request);
}
