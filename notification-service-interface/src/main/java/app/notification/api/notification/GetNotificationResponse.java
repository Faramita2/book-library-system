package app.notification.api.notification;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author meow
 */
public class GetNotificationResponse {
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
