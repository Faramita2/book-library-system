package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author zoo
 */
public class BOCreateUserRequest {
    @Property(name = "username")
    @NotNull
    @NotBlank
    @Length(min = 6)
    public String username;

    @Property(name = "email")
    @NotNull
    @NotBlank
    @Pattern("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    public String email;

    @Property(name = "password")
    @NotNull
    @Length(min = 6)
    @NotBlank
    public String password;

    @Property(name = "status")
    @NotNull
    public UserStatusView status;
}
