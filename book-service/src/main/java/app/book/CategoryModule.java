package app.book;

import app.book.category.domain.Category;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class CategoryModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Category.class);
    }
}
