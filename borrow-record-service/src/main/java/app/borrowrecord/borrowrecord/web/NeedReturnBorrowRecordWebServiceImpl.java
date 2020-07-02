package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.NeedReturnBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.ListNeedReturnBorrowRecordResponse;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class NeedReturnBorrowRecordWebServiceImpl implements NeedReturnBorrowRecordWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public ListNeedReturnBorrowRecordResponse list() {
        return service.findNeedReturnRecords();
    }
}
