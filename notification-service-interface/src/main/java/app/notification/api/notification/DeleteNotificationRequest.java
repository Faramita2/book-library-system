package app.notification.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

import java.util.List;


/**
 * @author zoo
 */
public class DeleteNotificationRequest {
    //TODO
    @QueryParam(name = "ids")
    @NotNull
    @NotBlank
    public List<String> ids;
}
