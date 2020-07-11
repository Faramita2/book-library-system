package app.website;

import app.website.api.BookCategoryAJAXWebService;
import app.website.service.BookCategoryService;
import app.website.api.BookCategoryAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookCategoryModule extends Module {
    @Override
    protected void initialize() {
        bind(BookCategoryService.class);
        api().service(BookCategoryAJAXWebService.class, bind(BookCategoryAJAXWebServiceImpl.class));
    }
}
