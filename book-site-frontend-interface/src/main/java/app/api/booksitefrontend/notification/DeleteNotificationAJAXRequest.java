package app.api.booksitefrontend.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class DeleteNotificationAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
