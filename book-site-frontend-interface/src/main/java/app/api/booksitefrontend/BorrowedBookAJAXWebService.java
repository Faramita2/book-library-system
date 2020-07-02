package app.api.booksitefrontend;

import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.booksitefrontend.borrowedbook.SearchBorrowedBookAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author meow
 */
public interface BorrowedBookAJAXWebService {
    @PUT
    @Path("/ajax/borrowed-book")
    SearchBorrowedBookAJAXResponse search(SearchBorrowedBookAJAXRequest request);
}
