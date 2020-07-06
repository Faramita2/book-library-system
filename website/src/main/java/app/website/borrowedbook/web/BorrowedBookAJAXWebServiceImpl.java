package app.website.borrowedbook.web;

import app.api.website.BorrowedBookAJAXWebService;
import app.api.website.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.website.borrowedbook.SearchBorrowedBookAJAXResponse;
import app.website.borrowedbook.service.BorrowedBookService;
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
