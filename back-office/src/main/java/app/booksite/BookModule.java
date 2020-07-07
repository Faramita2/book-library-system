package app.booksite;

import app.api.backoffice.BookAJAXWebService;
import app.booksite.book.service.BookService;
import app.booksite.web.BookAJAXWebServiceImpl;
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
