package app.website;

import app.api.website.BookAJAXWebService;
import app.website.service.BookService;
import app.website.api.BookAJAXWebServiceImpl;
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
