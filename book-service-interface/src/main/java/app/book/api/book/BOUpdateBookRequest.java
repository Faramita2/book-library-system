package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public class BOUpdateBookRequest {
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
