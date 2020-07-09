package app.api.website;

import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author meow
 */
public interface BorrowRecordAJAXWebService {
    @POST
    @Path("/ajax/borrow-record/:id/return")
    void returnBook(@PathParam("id") String id);
}
