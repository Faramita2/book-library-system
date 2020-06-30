package app.api.booksitefrontend.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author meow
 */
public class SearchBorrowedBookAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "books")
    public List<SearchBookAJAXResponse.Book> books;

    public static class Book {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;

        @NotNull
        @Property(name = "tag_names")
        public List<String> tagNames;

        @NotNull
        @NotBlank
        @Property(name = "description")
        public String description;

        @NotNull
        @Property(name = "category_names")
        public List<String> categoryNames;

        @NotNull
        @Property(name = "author_names")
        public List<String> authorNames;

        @NotNull
        @Property(name = "status")
        public BookStatusAJAXView status;
    }
}
