package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author meow
 */
public class SearchBorrowRecordRequest {
    @NotNull
    @Property(name = "borrower_id")
    public Long borrowerId;

    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @Property(name = "actual_return_at")
    public LocalDate actualReturnAt;

    @NotNull
    @Property(name = "skip")
    @Min(0)
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    @Max(1000)
    public Integer limit;
}
