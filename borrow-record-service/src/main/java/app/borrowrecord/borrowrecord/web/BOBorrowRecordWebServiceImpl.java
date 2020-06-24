package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordResponse;
import app.borrowrecord.borrowrecord.service.BOBorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOBorrowRecordWebServiceImpl implements BOBorrowRecordWebService {
    @Inject
    BOBorrowRecordService service;

    @Override
    public BOSearchBookBorrowRecordResponse search(BOSearchBookBorrowRecordRequest request) {
        return service.search(request);
    }
}
