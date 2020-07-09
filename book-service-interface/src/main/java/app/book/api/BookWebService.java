package app.book.api;

import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.BorrowBookRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author zoo
 */
public interface BookWebService {
    @PUT
    @Path("/book")
    SearchBookResponse search(SearchBookRequest request);

    @GET
    @Path("/book/:id")
    GetBookResponse get(@PathParam("id") Long id);

    @POST
    @Path("/book/:id/borrow")
    void borrow(@PathParam("id") Long id, BorrowBookRequest request);
}
