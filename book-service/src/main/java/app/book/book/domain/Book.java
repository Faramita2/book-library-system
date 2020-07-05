package app.book.book.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
@Table(name = "books")
public class Book {
    // todo
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

    @Column(name = "status")
    @NotNull
    public BookStatus status;

    @Column(name = "borrower_id")
    public Long borrowerId;

    @Column(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @Column(name = "return_at")
    public LocalDate returnAt;

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
