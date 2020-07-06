package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOGetAdminByAccountResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "account")
    public String account;

    @NotNull
    @NotBlank
    @Property(name = "salt")
    public String salt;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
