package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOBorrowRecordWebService {
    @PUT
    @Path("/borrow-record")
    BOSearchBookBorrowRecordResponse search(BOSearchBookBorrowRecordRequest request);
}
