package app.notification.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;


/**
 * @author zoo
 */
public class DeleteNotificationRequest {
    @QueryParam(name = "ids")
    @NotNull
    @NotBlank
    public String ids;
}
