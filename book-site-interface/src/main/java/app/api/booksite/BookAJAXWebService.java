package app.api.booksite;

import app.api.booksite.book.CreateBookAJAXRequest;
import app.api.booksite.book.GetBookAJAXResponse;
import app.api.booksite.book.SearchBookAJAXRequest;
import app.api.booksite.book.SearchBookAJAXResponse;
import app.api.booksite.book.UpdateBookAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BookAJAXWebService {
    @PUT
    @Path("/ajax/book")
    SearchBookAJAXResponse search(SearchBookAJAXRequest request);

    @GET
    @Path("/ajax/book/:id")
    GetBookAJAXResponse get(@PathParam("id") Long id);

    @POST
    @Path("/ajax/book")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookAJAXRequest request);

    @PUT
    @Path("/ajax/book/:id")
    void update(@PathParam("id") Long id, UpdateBookAJAXRequest request);
}
