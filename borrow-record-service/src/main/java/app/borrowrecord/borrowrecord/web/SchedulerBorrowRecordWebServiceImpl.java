package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.SchedulerBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.SchedulerSearchBorrowRecordResponse;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class SchedulerBorrowRecordWebServiceImpl implements SchedulerBorrowRecordWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public SchedulerSearchBorrowRecordResponse list() {
        return service.list();
    }
}
