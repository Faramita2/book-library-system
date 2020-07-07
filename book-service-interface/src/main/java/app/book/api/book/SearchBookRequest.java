package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchBookRequest {
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

    @Property(name = "status")
    public BookStatusView status;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
