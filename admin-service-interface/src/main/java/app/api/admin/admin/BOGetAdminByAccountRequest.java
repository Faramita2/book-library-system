package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOGetAdminByAccountRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "account")
    public String account;
}
