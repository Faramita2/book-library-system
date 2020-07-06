package app.website.borrowrecord.web;

import app.api.website.BorrowRecordAJAXWebService;
import app.api.website.book.BorrowBookAJAXRequest;
import app.website.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author meow
 */
public class BorrowRecordWebServiceImpl implements BorrowRecordAJAXWebService {
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
