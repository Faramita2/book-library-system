package app.booksitefrontend.bookauthor.web;

import app.api.booksitefrontend.BookAuthorAJAXWebService;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXResponse;
import app.booksitefrontend.bookauthor.service.BookAuthorService;
import app.booksitefrontend.user.web.UserPass;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookAuthorAJAXWebServiceImpl implements BookAuthorAJAXWebService {
    @Inject
    BookAuthorService service;

    @UserPass
    @Override
    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        return service.search(request);
    }
}
