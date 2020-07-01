package app.booksitefrontend;

import app.api.booksitefrontend.BookAuthorAJAXWebService;
import app.booksitefrontend.bookauthor.service.BookAuthorService;
import app.booksitefrontend.bookauthor.web.BookAuthorAJAXWebServiceImpl;
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