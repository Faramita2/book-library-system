package app.website.service;

import app.book.api.BorrowRecordWebService;
import app.book.api.borrowrecord.ReturnBookRequest;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BorrowRecordWebService borrowRecordWebService;

    public void returnBook(String id, Long userId, String username) {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        returnBookRequest.userId = userId;
        returnBookRequest.requestedBy = username;
        borrowRecordWebService.returnBook(id, returnBookRequest);
    }
}
