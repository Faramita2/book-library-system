package app.api.backoffice;

import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.backoffice.bookcategory.UpdateBookCategoryAJAXRequest;
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
public interface BookCategoryAJAXWebService {
    @PUT
    @Path("/ajax/book-category")
    SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request);

    @POST
    @Path("/ajax/book-category")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookCategoryAJAXRequest request);

    @PUT
    @Path("/ajax/book-category/:id")
    void update(@PathParam("id") Long id, UpdateBookCategoryAJAXRequest request);
}
