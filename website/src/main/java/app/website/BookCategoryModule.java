package app.website;

import app.api.website.BookCategoryAJAXWebService;
import app.website.bookcategory.service.BookCategoryService;
import app.website.bookcategory.web.BookCategoryAJAXWebServiceImpl;
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
