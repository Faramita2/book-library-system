package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class AuthorIdView {
    @NotNull
    @Column(name = "author_id")
    public Long authorId;
}
