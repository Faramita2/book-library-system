package app.book.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class GetBorrowRecordResponse {
    @NotNull
    @Property(name = "id")
    public String id;

    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;

    @Property(name = "actual_return_date")
    public LocalDate actualReturnDate;
}
