package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.BOGetBookBorrowRecordResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author zoo
 */
public interface BOBorrowRecordWebService {
    @GET
    @Path("/book/:id/borrow-record")
    BOGetBookBorrowRecordResponse get(@PathParam("id") Long id);
}
