package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BorrowRecordWebServiceImpl implements BorrowRecordWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public void create(CreateBorrowRecordRequest request) {
        service.create(request);
    }
}
