package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class BookAuthor {
    @NotNull
    @Column(name = "author_id")
    public Long authorId;

    @NotNull
    @Column(name = "book_id")
    public Long bookId;
}
