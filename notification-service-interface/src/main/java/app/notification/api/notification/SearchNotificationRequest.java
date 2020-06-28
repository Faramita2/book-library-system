package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class SearchNotificationRequest {
    @Property(name = "skip")
    @NotNull
    public Integer skip = 0;

    @Property(name = "limit")
    @NotNull
    public Integer limit = 1000;


    @Property(name = "user_id")
    @NotNull
    public Long userId;
}
