package app.book.api.author;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateAuthorRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;
}
