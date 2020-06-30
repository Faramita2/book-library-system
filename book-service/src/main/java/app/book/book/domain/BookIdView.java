package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class BookIdView {
    @Column(name = "book_id")
    @NotNull
    public Long bookId;
}
