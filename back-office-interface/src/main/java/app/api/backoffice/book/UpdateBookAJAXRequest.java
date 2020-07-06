package app.api.backoffice.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

import java.util.List;

/**
 * @author zoo
 */
public class UpdateBookAJAXRequest {
    //todo
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @Length(max = 255)
    @Property(name = "description")
    public String description;

    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @Property(name = "author_ids")
    public List<Long> authorIds;
}
