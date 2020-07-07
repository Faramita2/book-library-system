package app.api.authentication.authentication;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOLoginRequest {
    @NotNull
    @NotBlank
    @Property(name = "account")
    public String account;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
