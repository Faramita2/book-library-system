package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class LoginUserRequest {
    @Property(name = "username")
    @NotNull
    @NotBlank
    public String username;

    @Property(name = "password")
    @NotNull
    @NotBlank
    public String password;
}
