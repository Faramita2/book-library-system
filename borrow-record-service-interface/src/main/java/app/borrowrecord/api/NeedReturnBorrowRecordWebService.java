package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.ListNeedReturnBorrowRecordResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
// todo scheduler
public interface NeedReturnBorrowRecordWebService {
    @PUT
    @Path("/need-return-borrow-record")
    ListNeedReturnBorrowRecordResponse list();
}
