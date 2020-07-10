package app.website.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ResetPasswordAJAXRequest {
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
