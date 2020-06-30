package app.api.booksite;

import app.api.booksite.user.CreateUserAJAXRequest;
import app.api.booksite.user.ResetUserPasswordAJAXRequest;
import app.api.booksite.user.SearchUserAJAXRequest;
import app.api.booksite.user.SearchUserAJAXResponse;
import app.api.booksite.user.UpdateUserAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface UserAJAXWebService {
    @POST
    @Path("/ajax/user")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateUserAJAXRequest request);

    @PUT
    @Path("/ajax/user")
    SearchUserAJAXResponse search(SearchUserAJAXRequest request);

    @PUT
    @Path("/ajax/user/:id")
    void update(@PathParam("id") Long id, UpdateUserAJAXRequest request);

    @PUT
    @Path("/ajax/user/:id/reset-password")
    void resetPassword(@PathParam("id") Long id, ResetUserPasswordAJAXRequest request);
}
