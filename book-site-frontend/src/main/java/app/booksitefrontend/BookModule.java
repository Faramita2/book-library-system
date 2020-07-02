package app.booksitefrontend;

import app.api.booksitefrontend.BookAJAXWebService;
import app.booksitefrontend.book.service.BookService;
import app.booksitefrontend.book.web.BookAJAXWebServiceImpl;
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
