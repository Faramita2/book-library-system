package app.api.backoffice;

import app.api.backoffice.admin.LoginAJAXRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface AdminAJAXWebService {
    @PUT
    @Path("/ajax/login")
    void login(LoginAJAXRequest request);

    @PUT
    @Path("/ajax/logout")
    void logout();
}
