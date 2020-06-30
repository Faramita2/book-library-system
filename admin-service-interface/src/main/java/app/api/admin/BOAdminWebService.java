package app.api.admin;

import app.api.admin.admin.BOCreateAdminRequest;
import app.api.admin.admin.BOLoginAdminRequest;
import app.api.admin.admin.BOLoginAdminResponse;
import app.api.admin.admin.BOSearchAdminRequest;
import app.api.admin.admin.BOSearchAdminResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BOAdminWebService {
    @POST
    @Path("/bo/admin")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateAdminRequest request);

    @PUT
    @Path("/bo/admin")
    BOSearchAdminResponse search(BOSearchAdminRequest request);

    @PUT
    @Path("/bo/admin/login")
    BOLoginAdminResponse login(BOLoginAdminRequest request);
}
