package app.api.admin;

import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOAdminWebService {
    @PUT
    @Path("/bo/admin")
    BOGetAdminByAccountResponse getByAccount(BOGetAdminByAccountRequest request);
}
