package app.book.api;

import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
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
public interface BOAuthorWebService {
    @PUT
    @Path("/bo/author")
    BOSearchAuthorResponse search(BOSearchAuthorRequest request);

    @POST
    @Path("/bo/author")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateAuthorRequest request);

    @PUT
    @Path("/bo/author/:id")
    void update(@PathParam("id") Long id, BOUpdateAuthorRequest request);

    @DELETE
    @Path("/bo/author/:id")
    void delete(@PathParam("id") Long id);
}
