package app.book.api.book;

import app.book.api.author.AuthorView;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author zoo
 */
public class SearchBookResponse {
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

        @Property(name = "return_date")
        public LocalDate returnDate;
    }
}
