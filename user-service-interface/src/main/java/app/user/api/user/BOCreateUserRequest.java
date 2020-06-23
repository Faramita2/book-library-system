package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateUserRequest {
    @Property(name = "username")
    @NotNull
    @NotBlank
    @Length(min = 6)
    public String username;

    @Property(name = "password")
    @NotNull
    @Length(min = 6)
    @NotBlank
    public String password;

    @Property(name = "status")
    @NotNull
    public UserStatusView status;
}
