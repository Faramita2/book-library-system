package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class TagIdView {
    @NotNull
    @Column(name = "tag_id")
    public Long tagId;
}
