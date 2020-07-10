package app.backoffice;

import app.api.backoffice.BookCategoryAJAXWebService;
import app.backoffice.service.BookCategoryService;
import app.backoffice.api.BookCategoryAJAXWebServiceImpl;
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
