package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class UpdateBookRequest {
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @Property(name = "status")
    public BookStatusView status;

    @Property(name = "return_date")
    public LocalDate returnDate;

    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
