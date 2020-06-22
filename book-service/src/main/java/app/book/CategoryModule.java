package app.book;

import app.book.api.BOCategoryWebService;
import app.book.category.service.BOCategoryService;
import app.book.category.web.BOCategoryWebServiceImpl;
import app.book.category.domain.Category;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class CategoryModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Category.class);
        bind(BOCategoryService.class);
        api().service(BOCategoryWebService.class, bind(BOCategoryWebServiceImpl.class));
    }
}
