package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOBorrowRecordWebService {
    @PUT
    @Path("/bo/borrow-record")
    BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request);
}
