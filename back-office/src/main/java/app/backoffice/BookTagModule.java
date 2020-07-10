package app.backoffice;

import app.api.backoffice.BookTagAJAXWebService;
import app.backoffice.service.BookTagService;
import app.backoffice.api.BookTagAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookTagModule extends Module {
    @Override
    protected void initialize() {
        bind(BookTagService.class);
        api().service(BookTagAJAXWebService.class, bind(BookTagAJAXWebServiceImpl.class));
    }
}
