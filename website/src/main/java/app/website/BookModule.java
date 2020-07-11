package app.website;

import app.website.api.BookAJAXWebService;
import app.website.service.BookService;
import app.website.api.BookAJAXWebServiceImpl;
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
