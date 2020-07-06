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
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "authors")
    public List<Author> authors;

    @NotNull
    @Property(name = "categories")
    public List<Category> categories;

    @NotNull
    @Property(name = "tags")
    public List<Tag> tags;

    @NotNull
    @Property(name = "status")
    public BookStatusView status;

    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Property(name = "return_date")
    public LocalDate returnDate;

    public static class Author {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;
    }

    public static class Category {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;
    }

    public static class Tag {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;
    }
}
