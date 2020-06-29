package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOSearchUserRequest {
    @Property(name = "skip")
    @NotNull
    public Integer skip = 0;

    @Property(name = "limit")
    @NotNull
    public Integer limit = 1000;

    @Property(name = "username")
    public String username;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusView status;
}