package app.booksitefrontend;

import app.api.booksitefrontend.BookCategoryAJAXWebService;
import app.booksitefrontend.bookcategory.service.BookCategoryService;
import app.booksitefrontend.bookcategory.web.BookCategoryAJAXWebServiceImpl;
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
