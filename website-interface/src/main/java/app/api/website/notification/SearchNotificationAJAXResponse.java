package app.api.website.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meow
 */
public class SearchNotificationAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "notifications")
    public List<Notification> notifications;

    public static class Notification {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "content")
        public String content;

        @NotNull
        @Property(name = "created_at")
        public LocalDateTime createdAt;
    }
}
