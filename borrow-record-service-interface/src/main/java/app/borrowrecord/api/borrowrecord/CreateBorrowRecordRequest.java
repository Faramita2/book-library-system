package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class CreateBorrowRecordRequest {
    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @Property(name = "borrower_id")
    public Long borrowerId;

    @NotNull
    @NotBlank
    @Property(name = "book_name")
    public String bookName;

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
