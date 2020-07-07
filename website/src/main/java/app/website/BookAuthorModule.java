package app.website;

import app.api.website.BookAuthorAJAXWebService;
import app.website.service.BookAuthorService;
import app.website.api.BookAuthorAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookAuthorModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BookAuthorAJAXWebService.class, bind(BookAuthorAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BookAuthorService.class);
    }
}
