package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class CreateBorrowRecordRequest {
    @NotNull
    @Property(name = "book_id")
    public Long bookId;

    @NotNull
    @NotBlank
    @Property(name = "book_name")
    public String bookName;

    @NotNull
    @NotBlank
    @Property(name = "book_description")
    public String bookDescription;

    @NotNull
    @Size(min = 1)
    @Property(name = "authors")
    public List<Author> authors;

    @NotNull
    @Size(min = 1)
    @Property(name = "categories")
    public List<Category> categories;

    @NotNull
    @Size(min = 1)
    @Property(name = "tags")
    public List<Tag> tags;

    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @NotBlank
    @Property(name = "operator")
    public String operator;

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
