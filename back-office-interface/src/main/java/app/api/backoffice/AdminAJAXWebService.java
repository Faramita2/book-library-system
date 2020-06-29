package app.api.backoffice;

import app.api.backoffice.admin.LoginAdminAJAXRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface AdminAJAXWebService {
    @PUT
    @Path("/ajax/admin/login")
    void login(LoginAdminAJAXRequest request);
}
