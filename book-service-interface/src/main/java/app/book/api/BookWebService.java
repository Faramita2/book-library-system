package app.book.api;

import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.UpdateBookRequest;
import core.framework.api.web.service.GET;
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

    @PUT
    @Path("/book/:id")
    void update(@PathParam("id") Long id, UpdateBookRequest request);
}
