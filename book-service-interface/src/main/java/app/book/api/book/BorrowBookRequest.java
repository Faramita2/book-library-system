package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author zoo
 */
public class BorrowBookRequest {
    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @Property(name = "return_at")
    @NotNull
    public LocalDate returnAt;

    @Property(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
