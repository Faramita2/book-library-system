package app.book.api;

import app.book.api.borrowrecord.BOListBorrowRecordResponse;
import app.book.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.book.api.borrowrecord.BOSearchBorrowRecordResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BOBorrowRecordWebService {
    @PUT
    @Path("/bo/borrow-record")
    BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request);

    @GET
    @Path("/borrow-record")
    BOListBorrowRecordResponse list();
}
