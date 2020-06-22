package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author zoo
 */
public class BOUpdateBookRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @Property(name = "description")
    public String description;

    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @Property(name = "author_ids")
    public List<Long> authorIds;
}
