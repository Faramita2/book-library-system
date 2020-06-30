package app.api.booksitefrontend.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class LoginUserAJAXRequest {
    @Property(name = "username")
    @NotNull
    @NotBlank
    public String username;

    @Property(name = "password")
    @NotNull
    @NotBlank
    public String password;
}
