package app.backoffice;

import app.api.backoffice.BookAJAXWebService;
import app.backoffice.service.BookService;
import app.backoffice.api.BookAJAXWebServiceImpl;
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
