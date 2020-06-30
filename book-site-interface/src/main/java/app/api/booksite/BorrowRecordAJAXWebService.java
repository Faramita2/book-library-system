package app.api.booksite;

import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BorrowRecordAJAXWebService {
    @PUT
    @Path("/borrow-record")
    SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request);
}
