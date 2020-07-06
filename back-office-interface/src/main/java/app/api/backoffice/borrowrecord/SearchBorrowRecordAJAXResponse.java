package app.api.backoffice.borrowrecord;

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
        @Property(name = "borrowed_time")
        public LocalDateTime borrowedTime;

        @NotNull
        @Property(name = "return_date")
        public LocalDate returnDate;

        @Property(name = "actual_return_date")
        public LocalDate actualReturnDate;
    }
}
