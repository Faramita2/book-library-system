package app.api.backoffice.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchBookAJAXRequest {
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @Length(max = 255)
    @Property(name = "description")
    public String description;

    @Property(name = "author_ids")
    public List<Long> authorIds;

    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
