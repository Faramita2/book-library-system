package app.api.backoffice.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class UpdateUserAJAXRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
