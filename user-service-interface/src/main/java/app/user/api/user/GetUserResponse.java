package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class GetUserResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "username")
    @NotNull
    @NotBlank
    public String username;

    @Property(name = "status")
    @NotNull
    public UserStatusView status;
}
