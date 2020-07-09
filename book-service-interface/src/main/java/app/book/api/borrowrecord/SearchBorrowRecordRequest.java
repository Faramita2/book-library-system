package app.book.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class SearchBorrowRecordRequest {
    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
