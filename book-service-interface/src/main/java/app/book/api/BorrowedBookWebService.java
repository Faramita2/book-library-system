package app.book.api;

import app.book.api.borrowedbook.SearchBorrowedBookRequest;
import app.book.api.borrowedbook.SearchBorrowedBookResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BorrowedBookWebService {
    @PUT
    @Path("/borrowed-book")
    SearchBorrowedBookResponse search(SearchBorrowedBookRequest request);
}
