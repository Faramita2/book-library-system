package app.backoffice;

import app.api.backoffice.BookAJAXWebService;
import app.backoffice.book.service.BookService;
import app.backoffice.book.web.BookAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookModule extends Module {
    @Override
    protected void initialize() {
        bind(BookService.class);
        api().service(BookAJAXWebService.class, bind(BookAJAXWebServiceImpl.class));
    }
}
