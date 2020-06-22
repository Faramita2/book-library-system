package app.book.book.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
@Table(name = "books")
public class Book {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    public Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    public String name;

    @Column(name = "description")
    @NotNull
    @NotBlank
    public String description;

    @Column(name = "author_id")
    @NotNull
    public Long authorId;

    @Column(name = "tag_id")
    @NotNull
    public Long tagId;

    @Column(name = "category_id")
    @NotNull
    public Long categoryId;

    @Column(name = "status")
    @NotNull
    public BookStatus status;

    @Column(name = "borrower_id")
    public Long borrowerId;

    @Column(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @Column(name = "return_at")
    public LocalDateTime returnAt;

    @Column(name = "created_at")
    @NotNull
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    public LocalDateTime updatedAt;

    @Column(name = "created_by")
    @NotNull
    @NotBlank
    public String createdBy;

    @Column(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
