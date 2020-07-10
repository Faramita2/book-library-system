package app.website.api;

import app.website.api.bookauthor.SearchBookAuthorAJAXRequest;
import app.website.api.bookauthor.SearchBookAuthorAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BookAuthorAJAXWebService {
    @PUT
    @Path("/ajax/book-author")
    SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request);
}
