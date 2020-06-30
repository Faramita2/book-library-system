package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class ReturnBookRequest {
    @Property(name = "user_id")
    @NotNull
    public Long userId;

    @Property(name = "updated_by")
    @NotNull
    @NotBlank
    public String operator;
}
