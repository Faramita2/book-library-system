package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class BookCountView {
    @Column(name = "total")
    @NotNull
    public Long total;
}
