package app.website.api;

import app.website.api.book.GetBookAJAXResponse;
import app.website.api.book.SearchBookAJAXRequest;
import app.website.api.book.SearchBookAJAXResponse;
import app.website.api.book.SearchBorrowedBookAJAXRequest;
import app.website.api.book.SearchBorrowedBookAJAXResponse;
import app.website.api.borrowrecord.BorrowBookAJAXRequest;
import app.website.service.BookService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;
    @Inject
    WebContext webContext;

    @SkipLogin
    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return service.search(request);
    }

    @SkipLogin
    @Override
    public GetBookAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void borrow(Long id, BorrowBookAJAXRequest request) {
        ActionLogContext.put("id", id);
        service.borrow(id, request, userId(), username());
    }

    @Override
    public SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request) {
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return service.searchBorrowedBook(request, Long.valueOf(userId));
    }

    private Long userId() {
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return Long.valueOf(userId);
    }

    private String username() {
        return webContext.request().session().get("username").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
