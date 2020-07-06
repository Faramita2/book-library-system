package app.api.authentication.authentication;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class LoginRequest {
    @NotNull
    @NotBlank
    @Property(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
