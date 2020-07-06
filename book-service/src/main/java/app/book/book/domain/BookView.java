package app.book.book.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class BookView {
    @Column(name = "id")
    @NotNull
    public Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    public String name;

    @Column(name = "description")
    @NotNull
    @NotBlank
    public String description;

    @Column(name = "tag_name")
    @NotNull
    @NotBlank
    public String tagName;

    @Column(name = "category_name")
    @NotNull
    @NotBlank
    public String categoryName;

    @Column(name = "author_name")
    @NotNull
    @NotBlank
    public String authorName;

    @Column(name = "status")
    @NotNull
    public BookStatus status;

    @Column(name = "borrow_username")
    public String borrowUsername;

    @Column(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Column(name = "return_date")
    public LocalDate returnDate;
}
