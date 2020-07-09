package app.book.borrowrecord.web;

import app.book.api.BorrowRecordWebService;
import app.book.api.borrowrecord.ReturnBookRequest;
import app.book.api.borrowrecord.SearchBorrowRecordRequest;
import app.book.api.borrowrecord.SearchBorrowRecordResponse;
import app.book.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BorrowRecordWebServiceImpl implements BorrowRecordWebService {
    @Inject
    BorrowRecordService service;

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
