package app.website;

import app.api.website.BorrowRecordAJAXWebService;
import app.website.borrowrecord.service.BorrowRecordService;
import app.website.web.BorrowRecordAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
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
