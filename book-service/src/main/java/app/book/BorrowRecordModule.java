package app.book;

import app.book.api.BOBorrowRecordWebService;
import app.book.api.BorrowRecordWebService;
import app.book.borrowrecord.service.BOBorrowRecordService;
import app.book.borrowrecord.service.BorrowRecordService;
import app.book.borrowrecord.web.BOBorrowRecordWebServiceImpl;
import app.book.borrowrecord.web.BorrowRecordWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BorrowRecordModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BorrowRecordWebService.class, bind(BorrowRecordWebServiceImpl.class));
        api().service(BOBorrowRecordWebService.class, bind(BOBorrowRecordWebServiceImpl.class));
    }

    private void services() {
        bind(BOBorrowRecordService.class);
        bind(BorrowRecordService.class);
    }

    private void dbs() {
    }
}