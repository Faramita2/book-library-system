package app.book.api;

import app.book.api.borrowrecord.ReturnBookRequest;
import app.book.api.borrowrecord.SearchBorrowRecordRequest;
import app.book.api.borrowrecord.SearchBorrowRecordResponse;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author zoo
 */
public interface BorrowRecordWebService {
    @PUT
    @Path("/borrow-record")
    SearchBorrowRecordResponse search(SearchBorrowRecordRequest request);

    @POST
    @Path("/borrow-record/:id/return")
    void returnBook(@PathParam("id") String id, ReturnBookRequest request);
}
