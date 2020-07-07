package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author zoo
 */
@Table(name = "book_tags")
public class BookTag {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "tag_id")
    public Long tagId;

    @NotNull
    @Column(name = "book_id")
    public Long bookId;
}
