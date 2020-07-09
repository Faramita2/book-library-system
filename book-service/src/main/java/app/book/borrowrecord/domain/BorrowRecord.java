package app.book.borrowrecord.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
@Collection(name = "borrow_records")
public class BorrowRecord {
    @Id
    @Field(name = "_id")
    public ObjectId id;

    @NotNull
    @Field(name = "user")
    public User user;

    @NotNull
    @Field(name = "book")
    public Book book;

    @NotNull
    @Field(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @NotNull
    @Field(name = "return_date")
    public LocalDateTime returnDate;

    @Field(name = "actual_return_date")
    public LocalDateTime actualReturnDate;

    @NotNull
    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;

    @NotNull
    @NotBlank
    @Field(name = "created_by")
    public String createdBy;

    @NotNull
    @NotBlank
    @Field(name = "updated_by")
    public String updatedBy;

    public static class User {
        @NotNull
        @Field(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Field(name = "username")
        public String username;
    }

    public static class Book {
        @NotNull
        @Field(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Field(name = "name")
        public String name;

        @NotNull
        @NotBlank
        @Field(name = "description")
        public String description;

        @NotNull
        @Field(name = "authors")
        public List<Author> authors;

        @NotNull
        @Field(name = "categories")
        public List<Category> categories;

        @NotNull
        @Field(name = "tags")
        public List<Tag> tags;
    }

    public static class Author {
        @NotNull
        @Field(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Field(name = "name")
        public String name;
    }

    public static class Category {
        @NotNull
        @Field(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Field(name = "name")
        public String name;
    }

    public static class Tag {
        @NotNull
        @Field(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Field(name = "name")
        public String name;
    }
}
