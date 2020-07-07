package app.booksite;

import app.api.backoffice.BorrowRecordAJAXWebService;
import app.booksite.service.BorrowRecordService;
import app.booksite.api.BorrowRecordAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BorrowRecordAJAXWebService.class, bind(BorrowRecordAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(BorrowRecordService.class);
    }
}
