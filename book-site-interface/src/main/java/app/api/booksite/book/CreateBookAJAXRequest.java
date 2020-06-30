package app.api.booksite.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class CreateBookAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @NotNull
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @NotNull
    @Property(name = "author_ids")
    public List<Long> authorIds;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
