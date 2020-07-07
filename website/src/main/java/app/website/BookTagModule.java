package app.website;

import app.api.website.BookTagAJAXWebService;
import app.website.booktag.service.BookTagService;
import app.website.web.BookTagAJAXWebServiceImpl;
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
