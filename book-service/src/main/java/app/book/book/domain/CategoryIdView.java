package app.book.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;

/**
 * @author zoo
 */
public class CategoryIdView {
    @NotNull
    @Column(name = "category_id")
    public Long categoryId;
}
