package app.booksite;

import app.api.booksite.BookCategoryAJAXWebService;
import app.booksite.bookcategory.service.BookCategoryService;
import app.booksite.bookcategory.web.BookCategoryAJAXWebServiceImpl;
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
