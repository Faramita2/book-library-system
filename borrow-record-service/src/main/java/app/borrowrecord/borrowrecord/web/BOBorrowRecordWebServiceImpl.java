package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.BOListBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.borrowrecord.borrowrecord.service.BOBorrowRecordService;
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
