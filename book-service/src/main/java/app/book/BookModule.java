package app.book;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.book.service.BOBookService;
import app.book.book.service.BookService;
import app.book.book.web.BOBookWebServiceImpl;
import app.book.book.web.BookWebServiceImpl;
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
        api().service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));
        api().service(BookWebService.class, bind(BookWebServiceImpl.class));
    }

    private void services() {
        bind(BOBookService.class);
        bind(BookService.class);
    }
}
