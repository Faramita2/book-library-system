package app.book.api.borrowrecord;

import app.book.api.author.AuthorView;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

import java.time.LocalDate;
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
    public List<AuthorView> authors;

    @NotNull
    @Size(min = 1)
    @Property(name = "categories")
    public List<CategoryView> categories;

    @NotNull
    @Size(min = 1)
    @Property(name = "tags")
    public List<TagView> tags;

    @NotNull
    @Property(name = "borrow_user_id")
    public Long borrowUserId;

    @NotNull
    @Property(name = "borrow_username")
    public String borrowUsername;

    @NotNull
    @Property(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @NotBlank
    @Property(name = "requested_by")
    public String requestedBy;

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
