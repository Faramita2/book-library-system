package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOSearchAdminRequest {
    @NotNull
    @Property(name = "skip")
    @Min(0)
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    @Max(1000)
    public Integer limit;

    @Property(name = "account")
    public String account;
}
