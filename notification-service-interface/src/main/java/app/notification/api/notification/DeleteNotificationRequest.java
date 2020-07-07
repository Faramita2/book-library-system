package app.notification.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;


/**
 * @author zoo
 */
public class DeleteNotificationRequest {
    @NotNull
    @QueryParam(name = "user_id")
    public Long userId;

    @NotNull
    @NotBlank
    @QueryParam(name = "requested_by")
    public String requestedBy;
}
