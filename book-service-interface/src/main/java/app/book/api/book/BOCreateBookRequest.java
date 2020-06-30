package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class BOCreateBookRequest {
    @Property(name = "name")
    @NotNull
    @NotBlank
    public String name;

    @Property(name = "tag_ids")
    @NotNull
    public List<Long> tagIds;

    @Property(name = "description")
    @NotNull
    @NotBlank
    public String description;

    @Property(name = "category_ids")
    @NotNull
    public List<Long> categoryIds;

    @Property(name = "author_ids")
    @NotNull
    public List<Long> authorIds;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;
}
