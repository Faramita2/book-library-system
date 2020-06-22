package app.book;

import app.book.api.BOCategoryWebService;
import app.book.api.CategoryWebService;
import app.book.category.service.BOCategoryService;
import app.book.category.service.CategoryService;
import app.book.category.web.BOCategoryWebServiceImpl;
import app.book.category.domain.Category;
import app.book.category.web.CategoryWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class CategoryModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Category.class);
        bind(BOCategoryService.class);
        bind(CategoryService.class);
        api().service(BOCategoryWebService.class, bind(BOCategoryWebServiceImpl.class));
        api().service(CategoryWebService.class, bind(CategoryWebServiceImpl.class));
    }
}
