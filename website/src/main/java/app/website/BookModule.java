package app.website;

import app.api.website.BookAJAXWebService;
import app.website.book.service.BookService;
import app.website.web.BookAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BookService.class);
    }
}
