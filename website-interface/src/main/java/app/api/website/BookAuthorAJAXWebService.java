package app.api.website;

import app.api.website.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.website.bookauthor.SearchBookAuthorAJAXResponse;
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
