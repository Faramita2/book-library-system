package app.notification.api.notification.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class ReturnBorrowedBookMessage {
    @Property(name = "book_id")
    @NotNull
    public Long bookId;

    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @Property(name = "borrowed_at")
    @NotNull
    public LocalDateTime borrowedAt;

    @Property(name = "return_at")
    @NotNull
    public LocalDateTime returnAt;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
