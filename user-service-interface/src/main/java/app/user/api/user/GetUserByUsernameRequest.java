package app.user.api.user;

import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author zoo
 */
public class GetUserByUsernameRequest {
    @NotNull
    @NotBlank
    @Length(max = 30)
    @QueryParam(name = "username")
    public String username;
}