package app.book.api.borrowedbook;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchBorrowedBookRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;

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
