package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOSearchAdminRequest {
    @Property(name = "skip")
    @NotNull
    public Integer skip = 0;

    @Property(name = "limit")
    @NotNull
    public Integer limit = 1000;

    @Property(name = "account")
    public String account;
}
