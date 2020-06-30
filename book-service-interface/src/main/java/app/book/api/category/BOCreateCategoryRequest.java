package app.book.api.category;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateCategoryRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
