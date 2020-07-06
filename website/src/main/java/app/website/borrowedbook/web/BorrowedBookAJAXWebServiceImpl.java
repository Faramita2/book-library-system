package app.website.borrowedbook.web;

import app.api.website.BorrowRecordAJAXWebService;
import app.api.website.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.website.borrowrecord.SearchBorrowRecordAJAXResponse;
import app.website.borrowedbook.service.BorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class BorrowedBookAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request) {
        return service.search(request);
    }
}
