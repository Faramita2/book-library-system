package app.book.api;

import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOListTagResponse;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
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
public interface BOTagWebService {
    @PUT
    @Path("/bo/tag")
    BOSearchTagResponse search(BOSearchTagRequest request);

    @PUT
    @Path("/bo/tag")
    BOListTagResponse list();

    @POST
    @Path("/bo/tag")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateTagRequest request);

    @PUT
    @Path("/bo/tag/:id")
    void update(@PathParam("id") Long id, BOUpdateTagRequest request);

    @DELETE
    @Path("/bo/tag/:id")
    void delete(@PathParam("id") Long id);
}
