package app.notification.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;


/**
 * @author zoo
 */
public class DeleteBatchNotificationRequest {
    @NotNull
    @NotBlank
    @QueryParam(name = "ids")
    public String ids;

    @NotNull
    @NotBlank
    @QueryParam(name = "operator")
    public String operator;
}
