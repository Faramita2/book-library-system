package app.backoffice;

import app.api.backoffice.BorrowRecordAJAXWebService;
import app.backoffice.service.BorrowRecordService;
import app.backoffice.api.BorrowRecordAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        bind(BorrowRecordService.class);
        api().service(BorrowRecordAJAXWebService.class, bind(BorrowRecordAJAXWebServiceImpl.class));
    }
}
