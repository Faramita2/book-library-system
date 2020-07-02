package app.booksitefrontend;

import app.api.booksitefrontend.BorrowedBookAJAXWebService;
import app.booksitefrontend.borrowedbook.service.BorrowedBookService;
import app.booksitefrontend.borrowedbook.web.BorrowedBookAJAXWebServiceImpl;
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
