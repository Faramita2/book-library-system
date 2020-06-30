package app.booksite;

import app.api.booksite.BookTagAJAXWebService;
import app.booksite.booktag.service.BookTagService;
import app.booksite.booktag.web.BookTagAJAXWebServiceImpl;
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
