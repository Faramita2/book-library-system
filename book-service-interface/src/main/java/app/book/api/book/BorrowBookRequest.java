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

    @Property(name = "return_date")
    @NotNull
    public LocalDate returnDate;

    @Property(name = "operator")
    @NotNull
    @NotBlank
    public String operator;
}
