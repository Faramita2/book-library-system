package app.website;

import app.api.website.BorrowRecordAJAXWebService;
import app.website.borrowedbook.service.BorrowRecordService;
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
        api().service(BorrowRecordAJAXWebService.class, bind(BorrowedBookAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BorrowRecordService.class);
    }
}
