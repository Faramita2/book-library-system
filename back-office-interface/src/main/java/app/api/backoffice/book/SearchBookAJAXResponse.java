package app.api.backoffice.book;

import app.api.backoffice.bookauthor.BookAuthorAJAXView;
import app.api.backoffice.bookcategory.BookCategoryAJAXView;
import app.api.backoffice.booktag.BookTagAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchBookAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "books")
    public List<Book> books;

    public static class Book {
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
    }
}
