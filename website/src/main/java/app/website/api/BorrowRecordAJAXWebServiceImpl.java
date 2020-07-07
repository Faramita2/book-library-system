package app.website.api;

import app.api.website.BorrowRecordAJAXWebService;
import app.api.website.borrowrecord.BorrowBookAJAXRequest;
import app.website.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author meow
 */
public class BorrowRecordAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public void borrowBook(BorrowBookAJAXRequest request) {
        ActionLogContext.put("book_id", request.bookId);
        service.borrowBook(request);
    }

    @Override
    public void returnBook(String id) {
        ActionLogContext.put("id", id);
        service.returnBook(id);
    }
}
