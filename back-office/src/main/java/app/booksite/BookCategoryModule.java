package app.booksite;

import app.api.backoffice.BookCategoryAJAXWebService;
import app.booksite.service.BookCategoryService;
import app.booksite.api.BookCategoryAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookCategoryModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BookCategoryAJAXWebService.class, bind(BookCategoryAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BookCategoryService.class);
    }
}
