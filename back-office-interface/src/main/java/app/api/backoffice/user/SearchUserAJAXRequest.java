package app.api.backoffice.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;


/**
 * @author zoo
 */
public class SearchUserAJAXRequest {
    @Length(max = 30)
    @Property(name = "username")
    public String username;

    @Length(max = 255)
    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusAJAXView status;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
