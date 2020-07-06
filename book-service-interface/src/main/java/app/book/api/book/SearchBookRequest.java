package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
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

    @Length(max = 50)
    @Property(name = "description")
    public String description;

    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @Property(name = "author_ids")
    public List<Long> authorIds;

    @Property(name = "status")
    public BookStatusView status;

    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @Property(name = "return_date")
    public LocalDate returnDate;

    @Property(name = "actual_return_date")
    public LocalDate actualReturnDate;

    @NotNull
    @Property(name = "skip")
    @Min(0)
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    @Max(1000)
    public Integer limit;
}
