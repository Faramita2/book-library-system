package app.book.api.borrowedbook;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class SearchBorrowedBookResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "books")
    @NotNull
    public List<Book> books;

    public static class Book {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "name")
        @NotNull
        @NotBlank
        public String name;

        @Property(name = "tag_name")
        @NotNull
        @NotBlank
        public String tagName;

        @Property(name = "category_name")
        @NotNull
        @NotBlank
        public String categoryName;

        @Property(name = "author_name")
        @NotNull
        @NotBlank
        public String authorName;

        @Property(name = "borrower_name")
        public String borrowerName;

        @Property(name = "borrowed_at")
        public LocalDateTime borrowedAt;

        @Property(name = "return_at")
        public LocalDateTime returnAt;
    }
}
