package app.api.booksite.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchUserAJAXRequest {
    @Property(name = "ids")
    public List<Long> ids;

    @Property(name = "username")
    public String username;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusAJAXView status;

    @NotNull
    @Property(name = "skip")
    @Min(0)
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    @Max(1000)
    public Integer limit;
}
