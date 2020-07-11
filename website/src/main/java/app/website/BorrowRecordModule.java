package app.website;

import app.website.api.BorrowRecordAJAXWebService;
import app.website.service.BorrowRecordService;
import app.website.api.BorrowRecordAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        bind(BorrowRecordService.class);
        api().service(BorrowRecordAJAXWebService.class, bind(BorrowRecordAJAXWebServiceImpl.class));
    }
}
