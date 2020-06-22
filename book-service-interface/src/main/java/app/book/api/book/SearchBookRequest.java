package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class SearchBookRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;

    @Property(name = "name")
    public String name;

    @Property(name = "tag_id")
    public Long tagId;

    @Property(name = "description")
    public String description;

    @Property(name = "category_id")
    public Long categoryId;

    @Property(name = "author_id")
    public Long authorId;
}
