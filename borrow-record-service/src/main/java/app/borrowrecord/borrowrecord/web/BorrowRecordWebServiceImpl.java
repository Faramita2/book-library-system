package app.borrowrecord.borrowrecord.web;

import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.BorrowBookRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.ReturnBookRequest;
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
    public void borrowBook(BorrowBookRequest request) {
        service.borrowBook(request);
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
    public void returnBook(String id, ReturnBookRequest request) {
        ActionLogContext.put("id", id);
        service.returnBook(id, request);
    }
}
