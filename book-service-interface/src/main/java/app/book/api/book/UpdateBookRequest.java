package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author zoo
 */
public class UpdateBookRequest {
    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @NotNull
    @Property(name = "status")
    public BookStatusView status;

    @Property(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
