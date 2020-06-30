package app.user.api;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOResetUserPasswordRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BOUserWebService {
    @POST
    @Path("/bo/user")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateUserRequest request);

    @GET
    @Path("/bo/user/:id")
    BOGetUserResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/bo/user")
    BOSearchUserResponse search(BOSearchUserRequest request);

    @PUT
    @Path("/bo/user/:id")
    void update(@PathParam("id") Long id, BOUpdateUserRequest request);

    @PUT
    @Path("/bo/user/:id/reset-password")
    void resetPassword(@PathParam("id") Long id, BOResetUserPasswordRequest request);
}
