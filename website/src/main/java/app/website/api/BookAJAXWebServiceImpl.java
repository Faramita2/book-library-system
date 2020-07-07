package app.website.api;

import app.api.website.BookAJAXWebService;
import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.api.website.book.SearchBorrowedBookAJAXRequest;
import app.api.website.book.SearchBorrowedBookAJAXResponse;
import app.website.service.BookService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;

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
    public SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request) {
        return service.searchBorrowedBook(request);
    }
}
