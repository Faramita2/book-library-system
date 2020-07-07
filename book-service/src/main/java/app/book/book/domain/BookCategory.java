package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class BookCategory {
    @NotNull
    @Column(name = "category_id")
    public Long categoryId;

    @NotNull
    @Column(name = "book_id")
    public Long bookId;
}
