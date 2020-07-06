package app.api.website.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class LoginUserAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
