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
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "records")
    @NotNull
    public List<Record> records;

    public static class Record {
        @Property(name = "id")
        @NotNull
        public String id;

        @Property(name = "book_name")
        @NotNull
        public String bookName;

        @Property(name = "borrower_id")
        @NotNull
        public Long borrowerId;

        @Property(name = "borrower_name")
        @NotNull
        @NotBlank
        public String borrowerName;

        @Property(name = "borrowed_at")
        @NotNull
        public LocalDateTime borrowedAt;

        @Property(name = "return_at")
        @NotNull
        public LocalDate returnAt;
    }
}
