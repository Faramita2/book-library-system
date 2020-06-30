package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class SearchNotificationRequest {
    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @Property(name = "skip")
    @NotNull
    @Min(0)
    public Integer skip;

    @Property(name = "limit")
    @NotNull
    @Max(1000)
    public Integer limit;

}
