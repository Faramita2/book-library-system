package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meow
 */
public class ListNeedReturnBorrowRecordResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "records")
    public List<Record> records;

    public static class Record {
        @NotNull
        @Property(name = "id")
        public String id;

        @NotNull
        @Property(name = "borrower_id")
        public Long borrowerId;

        @NotNull
        @Property(name = "book_id")
        public Long bookId;

        @NotNull
        @Property(name = "borrowed_at")
        public LocalDateTime borrowedAt;

        @NotNull
        @Property(name = "return_at")
        public LocalDate returnAt;
    }
}
