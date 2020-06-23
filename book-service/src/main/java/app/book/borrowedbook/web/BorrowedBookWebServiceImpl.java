package app.book.borrowedbook.web;

import app.book.api.BorrowedBookWebService;
import app.book.api.borrowedbook.SearchBorrowedBookRequest;
import app.book.api.borrowedbook.SearchBorrowedBookResponse;
import app.book.borrowedbook.service.BorrowedBookService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BorrowedBookWebServiceImpl implements BorrowedBookWebService {
    @Inject
    BorrowedBookService service;

    @Override
    public SearchBorrowedBookResponse search(SearchBorrowedBookRequest request) {
        return service.search(request);
    }
}
