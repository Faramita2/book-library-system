package app.api.booksitefrontend;

import app.api.booksitefrontend.bookauthor.ListBookAuthorAJAXResponse;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BookAuthorAJAXWebService {
    @PUT
    @Path("/ajax/book-author")
    SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request);

    @PUT
    @Path("/ajax/book-author")
    ListBookAuthorAJAXResponse list();
}
