package app.book.book.web;

import app.book.api.BookWebService;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BookWebServiceImpl implements BookWebService {
    @Inject
    BookService service;

    @Override
    public SearchBookResponse search(SearchBookRequest request) {
        return service.search(request);
    }

    @Override
    public GetBookResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void borrow(Long id, BorrowBookRequest request) {
        ActionLogContext.put("book_id", id);
        service.borrow(id, request);
    }

    @Override
    public void returnBook(Long id) {

    }
}
