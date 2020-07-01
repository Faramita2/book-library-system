package app.api.booksite.bookcategory;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class CreateBookCategoryAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
