package app.book.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author meow
 */
public class ReturnBookRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "requested_by")
    public String requestedBy;
}
