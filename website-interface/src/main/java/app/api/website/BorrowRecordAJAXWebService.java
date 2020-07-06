package app.api.website;

import app.api.website.book.BorrowBookAJAXRequest;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author meow
 */
public interface BorrowRecordAJAXWebService {
    @POST
    @Path("/ajax/borrow-record")
    void borrowBook(BorrowBookAJAXRequest request);

    @POST
    @Path("/ajax/borrow-record/:id/return")
    void returnBook(@PathParam("id") String id);
}
