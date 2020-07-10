package app.website.api.notification;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.web.service.QueryParam;

/**
 * @author meow
 */
public class DeleteBatchNotificationAJAXRequest {
    @NotNull
    @NotBlank
    @QueryParam(name = "ids")
    public String ids;
}
