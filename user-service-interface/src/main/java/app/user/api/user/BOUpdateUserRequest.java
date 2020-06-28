package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public class BOUpdateUserRequest {
    @Property(name = "status")
    public UserStatusView status;
}
