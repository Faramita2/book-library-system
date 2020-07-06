package app.api.backoffice.bookcategory;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class CreateBookCategoryAJAXRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
