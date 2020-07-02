package app.api.booksite;

import app.api.booksite.booktag.CreateBookTagAJAXRequest;
import app.api.booksite.booktag.ListBookTagAJAXResponse;
import app.api.booksite.booktag.SearchBookTagAJAXRequest;
import app.api.booksite.booktag.SearchBookTagAJAXResponse;
import app.api.booksite.booktag.UpdateBookTagAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author zoo
 */
public interface BookTagAJAXWebService {
    @PUT
    @Path("/ajax/book-tag")
    SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request);

    @PUT
    @Path("/ajax/book-tag")
    ListBookTagAJAXResponse list();

    @POST
    @Path("/ajax/book-tag")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookTagAJAXRequest request);

    @PUT
    @Path("/ajax/book-tag/:id")
    void update(@PathParam("id") Long id, UpdateBookTagAJAXRequest request);
}
