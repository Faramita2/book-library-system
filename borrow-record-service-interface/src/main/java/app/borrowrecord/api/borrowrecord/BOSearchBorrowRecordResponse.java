package app.borrowrecord.api.borrowrecord;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class BOSearchBorrowRecordResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "records")
    public List<Record> records;

    public static class Record {
        @NotNull
        @Property(name = "id")
        public String id;

        @NotNull
        @Property(name = "user")
        public User user;

        @NotNull
        @Property(name = "book")
        public Book book;

        @NotNull
        @Property(name = "borrowed_time")
        public LocalDateTime borrowedTime;

        @NotNull
        @Property(name = "return_date")
        public LocalDate returnDate;

        @Property(name = "actual_return_date")
        public LocalDate actualReturnDate;
    }

    public static class User {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "username")
        public String username;
    }

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
        public List<Author> authors;

        @NotNull
        @Property(name = "categories")
        public List<Category> categories;

        @NotNull
        @Property(name = "tags")
        public List<Tag> tags;
    }

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
