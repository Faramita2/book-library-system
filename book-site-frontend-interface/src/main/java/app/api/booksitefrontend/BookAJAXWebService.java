package app.api.booksitefrontend;

import app.api.booksitefrontend.book.GetBookAJAXResponse;
import app.api.booksitefrontend.book.SearchBookAJAXRequest;
import app.api.booksitefrontend.book.SearchBookAJAXResponse;
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
    @Path("/ajax/book/:id/borrow")
    void borrow(@PathParam("id") Long id);

    @PUT
    @Path("/ajax/book/:id/return")
    void returnBook(@PathParam("id") Long id);
}
