package app.book.borrowedbook.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class BorrowedBook {
    @Column(name = "id")
    @NotNull
    public Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    public String name;

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

    @Column(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @Column(name = "return_at")
    public LocalDateTime returnAt;
}