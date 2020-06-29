package app.api.backoffice.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ResetUserPasswordAJAXRequest {
    @Property(name = "password")
    @NotNull
    @NotBlank
    @Length(min = 6)
    public String password;

    @Property(name = "password_confirm")
    @NotNull
    @NotBlank
    @Length(min = 6)
    public String passwordConfirm;
}