package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class CreateBorrowRecordRequest {
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

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
