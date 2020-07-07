package app.booksite;

import app.api.backoffice.BookTagAJAXWebService;
import app.booksite.service.BookTagService;
import app.booksite.api.BookTagAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookTagModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BookTagAJAXWebService.class, bind(BookTagAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BookTagService.class);
    }
}
