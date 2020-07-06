package app.api.website;

import app.api.website.bookcategory.ListBookCategoryAJAXResponse;
import app.api.website.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.website.bookcategory.SearchBookCategoryAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

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
}
