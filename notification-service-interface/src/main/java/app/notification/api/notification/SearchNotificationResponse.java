package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchNotificationResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "notifications")
    @NotNull
    public List<Notification> notifications;

    public static class Notification {
        @Property(name = "id")
        public Long id;

        @Property(name = "content")
        @NotNull
        @NotBlank
        public String content;
    }
}
