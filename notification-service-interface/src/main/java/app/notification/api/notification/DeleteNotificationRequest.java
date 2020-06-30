package app.notification.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;


/**
 * @author zoo
 */
public class DeleteNotificationRequest {
    @NotNull
    @NotBlank
    @QueryParam(name = "operator")
    public String operator;
}
