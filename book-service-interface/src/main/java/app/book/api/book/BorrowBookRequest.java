package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author zoo
 */
public class BorrowBookRequest {
    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @NotBlank
    @Property(name = "borrow_username")
    public String borrowUsername;

    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
