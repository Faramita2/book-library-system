package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

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

    @Override
    public GetBorrowRecordResponse get(String id) {
        return service.get(id);
    }

    @Override
    public SearchBorrowRecordResponse search(SearchBorrowRecordRequest request) {
        return service.search(request);
    }

    @Override
    public void update(String id, UpdateBorrowRecordRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request);
    }
}
