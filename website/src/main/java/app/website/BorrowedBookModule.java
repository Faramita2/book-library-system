package app.website;

import app.api.website.BorrowedBookAJAXWebService;
import app.website.borrowedbook.service.BorrowedBookService;
import app.website.borrowedbook.web.BorrowedBookAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class BorrowedBookModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BorrowedBookAJAXWebService.class, bind(BorrowedBookAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BorrowedBookService.class);
    }
}
