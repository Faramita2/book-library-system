package app.book.api.book;

import app.book.api.author.AuthorView;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class BOGetBookResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "authors")
    public List<AuthorView> authors;

    @NotNull
    @Property(name = "categories")
    public List<CategoryView> categories;

    @NotNull
    @Property(name = "tags")
    public List<TagView> tags;

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
