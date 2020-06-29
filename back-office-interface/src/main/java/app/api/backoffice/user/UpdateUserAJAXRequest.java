package app.api.backoffice.user;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public class UpdateUserAJAXRequest {
    @Property(name = "status")
    public UserStatusAJAXView status;
}
