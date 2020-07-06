package app.api.website;

import app.api.website.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.website.borrowrecord.SearchBorrowRecordAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface BorrowRecordAJAXWebService {
    @PUT
    @Path("/ajax/borrow-record")
    SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request);
}
