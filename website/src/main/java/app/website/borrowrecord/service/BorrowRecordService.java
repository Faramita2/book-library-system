package app.website.borrowrecord.service;

import app.api.website.book.BorrowBookAJAXRequest;
import app.book.api.BookWebService;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.ReturnBookRequest;
import app.borrowrecord.api.BorrowRecordWebService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BookWebService bookWebService;
    @Inject
    BorrowRecordWebService borrowRecordWebService;
    @Inject
    WebContext webContext;

    public void borrowBook(BorrowBookAJAXRequest request) {
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        String userId = getUserId();
        borrowBookRequest.userId = Long.valueOf(userId);
        borrowBookRequest.operator = "book-site-frontend";
        borrowBookRequest.returnDate = request.returnDate;
        bookWebService.borrow(request.bookId, borrowBookRequest);
    }

    public void returnBook(String id) {
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        String userId = getUserId();
        returnBookRequest.userId = Long.valueOf(userId);
        returnBookRequest.operator = getUsername();
        borrowRecordWebService.returnBook(id, returnBookRequest);
    }

    private String getUserId() {
        return webContext.request().session().get("user_id").orElseThrow(() ->
            new UnauthorizedException("please login first."));
    }

    private String getUsername() {
        return webContext.request().session().get("username").orElseThrow(() ->
            new UnauthorizedException("please login first."));
    }
}
