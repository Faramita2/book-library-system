package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOSearchBorrowRecordRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;

    @Property(name = "book_id")
    public Long bookId;
}
