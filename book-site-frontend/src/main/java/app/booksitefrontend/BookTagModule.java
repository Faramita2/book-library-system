package app.booksitefrontend;

import app.api.booksitefrontend.BookTagAJAXWebService;
import app.booksitefrontend.booktag.service.BookTagService;
import app.booksitefrontend.booktag.web.BookTagAJAXWebServiceImpl;
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
