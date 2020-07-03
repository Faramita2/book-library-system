package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import core.framework.api.http.HTTPStatus;
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
    void create(CreateBorrowRecordRequest request);

    @PUT
    @Path("/borrow-record")
    SearchBorrowRecordResponse search(SearchBorrowRecordRequest request);

    @PUT
    @Path("/borrow-record/:id")
    void update(@PathParam("id") String id, UpdateBorrowRecordRequest request);
}
