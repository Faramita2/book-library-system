package app.api.booksite.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class SearchBorrowRecordAJAXResponse {
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
        @Property(name = "book_name")
        public String bookName;

        @NotNull
        @Property(name = "borrower_id")
        public Long borrowerId;

        @NotNull
        @NotBlank
        @Property(name = "borrower_name")
        public String borrowerName;

        @NotNull
        @Property(name = "borrowed_at")
        public LocalDateTime borrowedAt;

        @NotNull
        @Property(name = "return_at")
        public LocalDate returnAt;

        @Property(name = "actual_return_at")
        public LocalDate actualReturnAt;
    }
}
