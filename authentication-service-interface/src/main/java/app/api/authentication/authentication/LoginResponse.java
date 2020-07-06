package app.api.authentication.authentication;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class LoginResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Property(name = "email")
    public String email;

    @NotNull
    @Property(name = "status")
    public UserStatusView status;

    public enum UserStatusView {
        @Property(name = "ACTIVE")
        ACTIVE,
        @Property(name = "INACTIVE")
        INACTIVE
    }
}
