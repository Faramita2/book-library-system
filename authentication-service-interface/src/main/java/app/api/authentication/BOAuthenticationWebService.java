package app.api.authentication;

import app.api.authentication.authentication.BOLoginRequest;
import app.api.authentication.authentication.BOLoginResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOAuthenticationWebService {
    @PUT
    @Path("/bo/login")
    BOLoginResponse login(BOLoginRequest request);
}
