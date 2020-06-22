package app.book.api;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
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
public interface BOBookWebService {
    @PUT
    @Path("/bo/book")
    BOSearchBookResponse search(BOSearchBookRequest request);

    @GET
    @Path("/bo/book/:id")
    BOGetBookResponse get(@PathParam("id") Long id);

    @POST
    @Path("/bo/book")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateBookRequest request);

    @PUT
    @Path("/bo/book/:id")
    void update(@PathParam("id") Long id, BOUpdateBookRequest request);
}
