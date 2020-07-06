package app.website.book.web;

import app.api.website.BookAJAXWebService;
import app.api.website.book.BorrowBookAJAXRequest;
import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.website.book.service.BookService;
import app.website.user.web.SkipLogin;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author meow
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;

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
        service.borrow(id, request);
    }

    @Override
    public void returnBook(Long id) {
        ActionLogContext.put("id", id);
        service.returnBook(id);
    }
}
