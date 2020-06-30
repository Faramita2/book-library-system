package app.booksitefrontend.book.web;

import app.api.booksitefrontend.BookAJAXWebService;
import app.api.booksitefrontend.book.GetBookAJAXResponse;
import app.api.booksitefrontend.book.SearchBookAJAXRequest;
import app.api.booksitefrontend.book.SearchBookAJAXResponse;
import app.api.booksitefrontend.book.SearchBorrowedBookAJAXRequest;
import app.api.booksitefrontend.book.SearchBorrowedBookAJAXResponse;
import app.booksitefrontend.book.service.BookService;
import app.booksitefrontend.user.web.UserPass;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;

    @UserPass
    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public SearchBorrowedBookAJAXResponse searchBorrowed(SearchBorrowedBookAJAXRequest request) {
        return service.searchBorrowed(request);
    }

    @UserPass
    @Override
    public GetBookAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void borrow(Long id) {

    }

    @Override
    public void returnBook(Long id) {

    }
}
