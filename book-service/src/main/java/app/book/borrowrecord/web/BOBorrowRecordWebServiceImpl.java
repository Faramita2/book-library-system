package app.book.borrowrecord.web;

import app.book.api.BOBorrowRecordWebService;
import app.book.api.borrowrecord.BOListBorrowRecordResponse;
import app.book.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.book.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.book.borrowrecord.service.BOBorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOBorrowRecordWebServiceImpl implements BOBorrowRecordWebService {
    @Inject
    BOBorrowRecordService service;

    @Override
    public BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request) {
        return service.search(request);
    }

    @Override
    public BOListBorrowRecordResponse list() {
        return service.list();
    }
}
