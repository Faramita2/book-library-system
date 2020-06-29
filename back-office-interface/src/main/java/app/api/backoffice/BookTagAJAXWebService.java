package app.api.backoffice;

import app.api.backoffice.booktag.CreateBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.api.backoffice.booktag.UpdateBookTagAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.DELETE;
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

    @POST
    @Path("/ajax/book-tag")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookTagAJAXRequest request);

    @PUT
    @Path("/ajax/book-tag/:id")
    void update(@PathParam("id") Long id, UpdateBookTagAJAXRequest request);

    @DELETE
    @Path("/ajax/book-tag/:id")
    void delete(@PathParam("id") Long id);
}
