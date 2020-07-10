package app.website.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author meow
 */
public class BorrowBookAJAXRequest {
    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;
}
