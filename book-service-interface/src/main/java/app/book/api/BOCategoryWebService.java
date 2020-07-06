package app.book.api;

import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import app.book.api.category.BOUpdateCategoryRequest;
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
public interface BOCategoryWebService {
    @PUT
    @Path("/bo/category")
    BOSearchCategoryResponse search(BOSearchCategoryRequest request);

    @POST
    @Path("/bo/category")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(BOCreateCategoryRequest request);

    @PUT
    @Path("/bo/category/:id")
    void update(@PathParam("id") Long id, BOUpdateCategoryRequest request);

    @DELETE
    @Path("/bo/category/:id")
    void delete(@PathParam("id") Long id);
}
