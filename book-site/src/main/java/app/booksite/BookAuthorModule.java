package app.booksite;

import app.api.booksite.BookAuthorAJAXWebService;
import app.booksite.bookauthor.service.BookAuthorService;
import app.booksite.bookauthor.web.BookAuthorAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookAuthorModule extends Module {
    @Override
    protected void initialize() {
        bind(BookAuthorService.class);
        api().service(BookAuthorAJAXWebService.class, bind(BookAuthorAJAXWebServiceImpl.class));
    }
}
