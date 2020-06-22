package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOCreateBookRequest {
    @Property(name = "name")
    @NotNull
    @NotBlank
    public String name;

    @Property(name = "tag_id")
    @NotNull
    public Long tagId;

    @Property(name = "description")
    @NotNull
    @NotBlank
    public String description;

    @Property(name = "category_id")
    @NotNull
    public Long categoryId;

    @Property(name = "author_id")
    @NotNull
    public Long authorId;
}
