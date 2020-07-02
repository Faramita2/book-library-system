package app.api.booksite;

import app.api.booksite.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.ListBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.UpdateBookCategoryAJAXRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.GET;
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

    @GET
    @Path("/ajax/book-category")
    ListBookCategoryAJAXResponse list();

    @POST
    @Path("/ajax/book-category")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateBookCategoryAJAXRequest request);

    @PUT
    @Path("/ajax/book-category/:id")
    void update(@PathParam("id") Long id, UpdateBookCategoryAJAXRequest request);

    @DELETE
    @Path("/ajax/book-category/:id")
    void delete(@PathParam("id") Long id);
}
