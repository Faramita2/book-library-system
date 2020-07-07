package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author zoo
 */
@Table(name = "book_categories")
public class BookCategory {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "category_id")
    public Long categoryId;

    @NotNull
    @Column(name = "book_id")
    public Long bookId;
}
