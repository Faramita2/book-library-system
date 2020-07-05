package app.api.booksitefrontend;

import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXResponse;
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
