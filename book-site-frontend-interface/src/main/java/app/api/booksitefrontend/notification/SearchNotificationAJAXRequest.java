package app.api.booksitefrontend.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class SearchNotificationAJAXRequest {
    @Property(name = "skip")
    @NotNull
    @Min(0)
    public Integer skip;

    @Property(name = "limit")
    @NotNull
    @Max(1000)
    public Integer limit;
}
