package app.api.backoffice;

import app.api.backoffice.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.backoffice.bookauthor.UpdateBookAuthorAJAXRequest;
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
public interface BookAuthorAJAXWebService {
    @PUT
    @Path("/ajax/book-author")
    SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request);

    @POST
    @Path("/ajax/book-author")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookAuthorAJAXRequest request);

    @PUT
    @Path("/ajax/book-author/:id")
    void update(@PathParam("id") Long id, UpdateBookAuthorAJAXRequest request);
}
