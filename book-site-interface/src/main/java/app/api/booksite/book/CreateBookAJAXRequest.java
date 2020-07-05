package app.api.booksite.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class CreateBookAJAXRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @NotNull
    @NotBlank
    @Length(max = 255)
    @Property(name = "description")
    public String description;

    // todo think deep
    // size
    @NotNull
    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @NotNull
    @Property(name = "author_ids")
    public List<Long> authorIds;
}
