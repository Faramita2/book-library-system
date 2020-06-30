package app.api.booksitefrontend;

import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXResponse;
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
