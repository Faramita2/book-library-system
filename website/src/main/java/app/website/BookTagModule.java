package app.website;

import app.website.api.BookTagAJAXWebService;
import app.website.service.BookTagService;
import app.website.api.BookTagAJAXWebServiceImpl;
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
