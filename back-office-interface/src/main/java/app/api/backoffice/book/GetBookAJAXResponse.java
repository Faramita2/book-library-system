package app.api.backoffice.book;

import app.api.backoffice.bookauthor.BookAuthorAJAXView;
import app.api.backoffice.bookcategory.BookCategoryAJAXView;
import app.api.backoffice.booktag.BookTagAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
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
