package app.api.website;

import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.api.website.book.SearchBorrowedBookAJAXRequest;
import app.api.website.book.SearchBorrowedBookAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author meow
 */
public interface BookAJAXWebService {
    @PUT
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @GET
    @Path("/ajax/book/:id")
    GetBookAJAXResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/borrowed-book")
    SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request);
}