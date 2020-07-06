package app.api.website;

import app.api.website.user.LoginUserAJAXRequest;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface UserAJAXWebService {
    //todo logout
    @PUT
    @Path("/ajax/user/login")
    void login(LoginUserAJAXRequest request);
}
