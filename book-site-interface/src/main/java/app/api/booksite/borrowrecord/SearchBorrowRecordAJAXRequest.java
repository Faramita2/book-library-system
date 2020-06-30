package app.api.booksite.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class SearchBorrowRecordAJAXRequest {
    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @Property(name = "skip")
    @Min(0)
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    @Max(1000)
    public Integer limit;
}
