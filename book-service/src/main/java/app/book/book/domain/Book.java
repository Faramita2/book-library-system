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
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Column(name = "description")
    public String description;

    @NotNull
    @Column(name = "status")
    public BookStatus status;

    @Column(name = "borrow_user_id")
    public Long borrowUserId;

    @Column(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Column(name = "return_date")
    public LocalDate returnDate;

    @NotNull
    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @Column(name = "updated_time")
    public LocalDateTime updatedTime;

    @NotNull
    @NotBlank
    @Column(name = "created_by")
    public String createdBy;

    @NotNull
    @NotBlank
    @Column(name = "updated_by")
    public String updatedBy;
}
