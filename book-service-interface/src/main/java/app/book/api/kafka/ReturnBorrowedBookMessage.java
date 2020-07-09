package app.book.api.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
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
    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
