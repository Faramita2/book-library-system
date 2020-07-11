package app.book;

import app.book.api.BOCategoryWebService;
import app.book.api.CategoryWebService;
import app.book.book.domain.BookCategory;
import app.book.category.domain.Category;
import app.book.category.service.BOCategoryService;
import app.book.category.service.CategoryService;
import app.book.category.web.BOCategoryWebServiceImpl;
import app.book.category.web.CategoryWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class CategoryModule extends Module {
    @Override
    protected void initialize() {
        dbs();
        services();
        apiServices();
    }

    private void apiServices() {
        api().service(BOCategoryWebService.class, bind(BOCategoryWebServiceImpl.class));
        api().service(CategoryWebService.class, bind(CategoryWebServiceImpl.class));
    }

    private void services() {
        bind(BOCategoryService.class);
        bind(CategoryService.class);
    }

    private void dbs() {
        db().repository(Category.class);
        db().repository(BookCategory.class);
    }
}
