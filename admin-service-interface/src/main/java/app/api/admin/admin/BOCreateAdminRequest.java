package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateAdminRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "account")
    public String account;

    @NotNull
    @NotBlank
    @Property(name = "password")
    @Length(min = 6)
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
