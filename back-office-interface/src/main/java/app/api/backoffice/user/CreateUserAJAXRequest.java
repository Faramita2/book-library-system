package app.api.backoffice.user;

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
    @Length(min = 6, max = 50)
    @Property(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Length(max = 255)
    @Pattern("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    @Property(name = "email")
    public String email;

    // todo length max
    @NotNull
    @NotBlank
    @Length(min = 6)
    @Property(name = "password")
    public String password;

    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
