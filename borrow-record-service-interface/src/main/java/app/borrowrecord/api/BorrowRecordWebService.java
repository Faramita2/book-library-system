package app.borrowrecord.api;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BorrowRecordWebService {
    @POST
    @Path("/borrow-record")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBorrowRecordRequest request);
}
