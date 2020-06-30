package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOResetUserPasswordRequest {
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

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
