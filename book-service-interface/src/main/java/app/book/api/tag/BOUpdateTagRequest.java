package app.book.api.tag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOUpdateTagRequest {
    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
