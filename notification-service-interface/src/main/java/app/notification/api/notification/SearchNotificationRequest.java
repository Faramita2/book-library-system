package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class SearchNotificationRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;

}
