package app.book.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meow
 */
public class BOListBorrowRecordResponse {
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
        @Property(name = "borrow_user_id")
        public Long borrowUserId;

        @NotNull
        @Property(name = "book_id")
        public Long bookId;

        @NotNull
        @Property(name = "borrowed_time")
        public LocalDateTime borrowedTime;

        @NotNull
        @Property(name = "return_date")
        public LocalDate returnDate;
    }
}
