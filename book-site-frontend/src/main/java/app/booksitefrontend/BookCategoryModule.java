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
