package app.website.api;

import app.website.api.bookcategory.SearchBookCategoryAJAXRequest;
import app.website.api.bookcategory.SearchBookCategoryAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BookCategoryAJAXWebService {
    @PUT
    @Path("/ajax/book-category")
    SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request);
}
