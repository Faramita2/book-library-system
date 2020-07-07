package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Max;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author meow
 */
public class SearchBorrowRecordRequest {
    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @Length(max = 50)
    @Property(name = "book_name")
    public String bookName;

    @Length(max = 255)
    @Property(name = "book_description")
    public String bookDescription;

    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @Property(name = "author_ids")
    public List<Long> authorIds;

    @Property(name = "borrow_date")
    public LocalDate borrowedDate;

    @Property(name = "return_date")
    public LocalDate returnDate;

    @Property(name = "actual_return_date")
    public LocalDate actualReturnDate;

    @NotNull
    @Min(0)
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Max(1000)
    @Property(name = "limit")
    public Integer limit;
}
