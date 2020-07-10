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
