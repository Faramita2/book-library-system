package app.user.api;

import app.user.api.user.BOCreateUserRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BOUserWebService {
    @POST
    @Path("/bo/user")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateUserRequest request);
}
