package app.api.website.book;

import app.api.website.bookauthor.BookAuthorAJAXView;
import app.api.website.bookcategory.BookCategoryAJAXView;
import app.api.website.booktag.BookTagAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author meow
 */
public class GetBookAJAXResponse {
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
    public List<BookAuthorAJAXView> authors;

    @NotNull
    @Property(name = "categories")
    public List<BookCategoryAJAXView> categories;

    @NotNull
    @Property(name = "tags")
    public List<BookTagAJAXView> tags;

    @NotNull
    @Property(name = "status")
    public BookStatusAJAXView status;

    @Property(name = "borrow_username")
    public String borrowUsername;

    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Property(name = "return_date")
    public LocalDate returnDate;
}
