package app.api.booksite.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author zoo
 */
public class CreateUserAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "username")
    @Length(min = 6)
    public String username;

    @NotNull
    @NotBlank
    @Property(name = "email")
    @Pattern("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    public String email;

    @NotNull
    @NotBlank
    @Property(name = "password")
    @Length(min = 6)
    public String password;

    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
