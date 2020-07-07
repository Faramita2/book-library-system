package app.booksite.api;

import app.api.backoffice.BorrowRecordAJAXWebService;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXResponse;
import app.booksite.service.BorrowRecordService;
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
