package app.booksite;

import app.api.booksite.BookAJAXWebService;
import app.booksite.book.service.BookService;
import app.booksite.book.web.BookAJAXWebServiceImpl;
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
