package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class BOGetBookBorrowRecordResponse {
    @Property(name = "id")
    @NotNull
    public String id;

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
    public LocalDateTime returnAt;
}
