package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class GetBookResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "tag_ids")
    public List<Long> tagIds;

    @NotNull
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "category_ids")
    public List<Long> categoryIds;

    @NotNull
    @Property(name = "author_ids")
    public List<Long> authorIds;

    @NotNull
    @Property(name = "status")
    public BookStatusView status;

    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Property(name = "return_date")
    public LocalDate returnDate;
}
