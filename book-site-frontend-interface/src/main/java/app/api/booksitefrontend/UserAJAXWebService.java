package app.api.booksitefrontend;

import app.api.booksitefrontend.user.LoginUserAJAXRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface UserAJAXWebService {
    @PUT
    @Path("/ajax/user/login")
    void login(LoginUserAJAXRequest request);
}
