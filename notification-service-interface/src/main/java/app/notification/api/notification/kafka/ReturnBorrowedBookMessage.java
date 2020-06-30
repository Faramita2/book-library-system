package app.notification.api.notification.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class ReturnBorrowedBookMessage {
    @NotNull
    @Property(name = "book_name")
    public String bookName;

    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @NotNull
    @Property(name = "return_at")
    public LocalDateTime returnAt;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
