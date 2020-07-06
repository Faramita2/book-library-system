package app.api.authentication.authentication;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ResetPasswordRequest {
    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "token")
    public String token;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
