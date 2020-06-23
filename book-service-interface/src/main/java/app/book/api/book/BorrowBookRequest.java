package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class BorrowBookRequest {
    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @Property(name = "return_at")
    @NotNull
    public LocalDateTime returnAt;

    @Property(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
