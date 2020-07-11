package app.website;

import app.website.api.BookAuthorAJAXWebService;
import app.website.service.BookAuthorService;
import app.website.api.BookAuthorAJAXWebServiceImpl;
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
