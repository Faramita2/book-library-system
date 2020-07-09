package app.api.website;

import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.api.website.book.SearchBorrowedBookAJAXRequest;
import app.api.website.book.SearchBorrowedBookAJAXResponse;
import app.api.website.borrowrecord.BorrowBookAJAXRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
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

    @POST
    @Path("/ajax/book/:id/borrow")
    void borrow(@PathParam("id") Long id, BorrowBookAJAXRequest request);

    @PUT
    @Path("/ajax/borrowed-book")
    SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request);
}
