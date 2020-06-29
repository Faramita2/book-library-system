package app.api.backoffice;

import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXResponse;
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
