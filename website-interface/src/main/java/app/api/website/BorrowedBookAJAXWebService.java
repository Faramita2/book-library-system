package app.api.website;

import app.api.website.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.website.borrowedbook.SearchBorrowedBookAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface BorrowedBookAJAXWebService {
    // todo
    @PUT
    @Path("/ajax/borrow-book")
    SearchBorrowedBookAJAXResponse search(SearchBorrowedBookAJAXRequest request);
}
