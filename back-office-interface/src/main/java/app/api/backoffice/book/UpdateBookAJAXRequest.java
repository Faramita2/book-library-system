package app.api.backoffice.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

import java.util.List;

/**
 * @author zoo
 */
public class UpdateBookAJAXRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Length(max = 255)
    @Property(name = "description")
    public String description;

    @NotNull
    @Size(min = 1)
    @Property(name = "author_ids")
    public List<Long> authorIds;

    @NotNull
    @Size(min = 1)
    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @NotNull
    @Size(min = 1)
    @Property(name = "tag_ids")
    public List<Long> tagIds;
}
