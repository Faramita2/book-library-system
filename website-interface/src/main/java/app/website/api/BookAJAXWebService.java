package app.website.api;

import app.website.api.book.GetBookAJAXResponse;
import app.website.api.book.SearchBookAJAXRequest;
import app.website.api.book.SearchBookAJAXResponse;
import app.website.api.book.SearchBorrowedBookAJAXRequest;
import app.website.api.book.SearchBorrowedBookAJAXResponse;
import app.website.api.borrowrecord.BorrowBookAJAXRequest;
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
