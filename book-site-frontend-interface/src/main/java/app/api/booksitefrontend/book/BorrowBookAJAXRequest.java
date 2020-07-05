package app.api.booksitefrontend.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author meow
 */
public class BorrowBookAJAXRequest {
    // todo
    @NotNull
    @Property(name = "return_at")
    public LocalDate returnAt;
}
