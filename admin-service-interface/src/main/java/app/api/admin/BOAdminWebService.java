package app.api.admin;

import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOAdminWebService {
    @GET
    @Path("/bo/admin")
    BOGetAdminByAccountResponse getByAccount(BOGetAdminByAccountRequest request);
}
