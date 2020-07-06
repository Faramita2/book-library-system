package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;

import java.time.LocalDate;

/**
 * @author meow
 */
public class UpdateBorrowRecordRequest {
    @NotNull
    @Property(name = "actual_return_date")
    public LocalDate actualReturnDate;
}
