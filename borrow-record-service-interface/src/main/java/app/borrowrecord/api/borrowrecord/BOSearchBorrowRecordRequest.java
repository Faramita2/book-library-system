package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOSearchBorrowRecordRequest {
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
