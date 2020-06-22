package app.book.api.tag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateTagRequest {
    @Property(name = "name")
    @NotNull
    @NotBlank
    public String name;
}
