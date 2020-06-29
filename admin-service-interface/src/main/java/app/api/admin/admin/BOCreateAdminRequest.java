package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateAdminRequest {
    @Property(name = "account")
    @NotNull
    @NotBlank
    public String account;

    @Property(name = "password")
    @NotNull
    @NotBlank
    @Length(min = 6)
    public String password;

    @Property(name = "created_by")
    @NotNull
    @NotBlank
    public String createdBy;
}
