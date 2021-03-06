package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOUpdateUserRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusView status;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
