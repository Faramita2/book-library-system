package app.booksite;

import app.api.booksite.BorrowRecordAJAXWebService;
import app.booksite.borrowrecord.service.BorrowRecordService;
import app.booksite.borrowrecord.web.BorrowRecordAJAXWebServiceImpl;
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
