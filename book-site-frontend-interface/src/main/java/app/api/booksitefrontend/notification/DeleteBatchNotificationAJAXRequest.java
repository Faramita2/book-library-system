package app.api.booksitefrontend.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author meow
 */
public class DeleteBatchNotificationAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "ids")
    public List<String> ids;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
