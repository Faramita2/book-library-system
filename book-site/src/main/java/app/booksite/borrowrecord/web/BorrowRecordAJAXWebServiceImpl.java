package app.booksite.borrowrecord.web;

import app.api.booksite.BorrowRecordAJAXWebService;
import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXResponse;
import app.booksite.borrowrecord.service.BorrowRecordService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class BorrowRecordAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;

    @Override
    public SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request) {
        return service.search(request);
    }
}
