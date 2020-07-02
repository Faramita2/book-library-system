package app.booksitefrontend.borrowedbook.web;

import app.api.booksitefrontend.BorrowedBookAJAXWebService;
import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXResponse;
import app.booksitefrontend.borrowedbook.service.BorrowedBookService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class BorrowedBookAJAXWebServiceImpl implements BorrowedBookAJAXWebService {
    @Inject
    BorrowedBookService service;

    @Override
    public SearchBorrowedBookAJAXResponse search(SearchBorrowedBookAJAXRequest request) {
        return service.search(request);
    }
}
