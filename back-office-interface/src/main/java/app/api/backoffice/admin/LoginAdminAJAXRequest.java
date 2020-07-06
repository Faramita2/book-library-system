package app.api.backoffice.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class LoginAdminAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "account")
    public String account;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
