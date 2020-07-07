package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class GetUserByUsernameResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "email")
    public String email;

    @NotNull
    @NotBlank
    @Property(name = "salt")
    public String salt;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;

    @NotNull
    @Property(name = "status")
    public UserStatusView status;
}
