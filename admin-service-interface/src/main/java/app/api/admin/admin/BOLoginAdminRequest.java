package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOLoginAdminRequest {
    @Property(name = "account")
    @NotNull
    @NotBlank
    public String account;

    @Property(name = "password")
    @NotNull
    @NotBlank
    public String password;
}
