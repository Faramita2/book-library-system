package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;


/**
 * @author zoo
 */
public class DeleteNotificationRequest {
    @NotNull
    @NotBlank
    @Property(name = "ids")
    public List<String> ids;
}
