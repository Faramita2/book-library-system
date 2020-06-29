package app.api.admin;

import app.api.admin.admin.BOCreateAdminRequest;
import app.api.admin.admin.BOLoginAdminRequest;
import app.api.admin.admin.BOSearchAdminRequest;
import app.api.admin.admin.BOSearchAdminResponse;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOAdminWebService {
    @POST
    @Path("/bo/admin")
    void create(BOCreateAdminRequest request);

    @PUT
    @Path("/bo/admin")
    BOSearchAdminResponse search(BOSearchAdminRequest request);

    @PUT
    @Path("/bo/admin/login")
    void login(BOLoginAdminRequest request);
}
