package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ResetUserPasswordRequest {
    @NotNull
    @NotBlank
    @Length(min = 6)
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
