package app.api.booksite.booktag;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class CreateBookTagAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;
}
