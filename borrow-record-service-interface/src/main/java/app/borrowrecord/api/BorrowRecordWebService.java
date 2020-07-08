package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.BorrowBookRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.ReturnBookRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BorrowRecordWebService {
    @POST
    @Path("/borrow-record")
    @ResponseStatus(HTTPStatus.CREATED)
    void borrowBook(BorrowBookRequest request);

    @GET
    @Path("/borrow-record/:id")
    GetBorrowRecordResponse get(@PathParam("id") String id);

    @PUT
    @Path("/borrow-record")
    SearchBorrowRecordResponse search(SearchBorrowRecordRequest request);

    @PUT
    @Path("/borrow-record/:id/return")
    void returnBook(@PathParam("id") String id, ReturnBookRequest request);
}
