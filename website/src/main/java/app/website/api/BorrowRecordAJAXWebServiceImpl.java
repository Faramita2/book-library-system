package app.website.api;

import app.api.website.BorrowRecordAJAXWebService;
import app.api.website.borrowrecord.BorrowBookAJAXRequest;
import app.website.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class BorrowRecordAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;
    @Inject
    WebContext webContext;

    @Override
    public void borrowBook(BorrowBookAJAXRequest request) {
        ActionLogContext.put("book_id", request.bookId);
        service.borrowBook(request, userId(), username());
    }

    @Override
    public void returnBook(String id) {
        ActionLogContext.put("id", id);
        service.returnBook(id, userId(), username());
    }

    private Long userId() {
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return Long.valueOf(userId);
    }

    private String username() {
        return webContext.request().session().get("username").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
