package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class BOSearchUserRequest {
    @Property(name = "ids")
    public List<Long> ids;

    @Length(max = 50)
    @Property(name = "username")
    public String username;

    @Length(max = 255)
    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusView status;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
