package app.api.booksitefrontend.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author meow
 */
public class SearchNotificationAJAXResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @NotNull
    @Property(name = "notifications")
    public List<Notification> notifications;

    public static class Notification {
        @NotNull
        @Property(name = "id")
        public Long id;

        @Property(name = "content")
        @NotNull
        @NotBlank
        public String content;
    }
}
