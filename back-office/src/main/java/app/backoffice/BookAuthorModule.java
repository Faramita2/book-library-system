package app.backoffice;

import app.api.backoffice.BookAuthorAJAXWebService;
import app.backoffice.service.BookAuthorService;
import app.backoffice.api.BookAuthorAJAXWebServiceImpl;
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
