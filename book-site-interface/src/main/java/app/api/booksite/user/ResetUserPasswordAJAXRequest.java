package app.api.booksite.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ResetUserPasswordAJAXRequest {
    // todo
    @NotNull
    @NotBlank
    @Property(name = "password")
    @Length(min = 6)
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "password_confirm")
    @Length(min = 6)
    public String passwordConfirm;
}
