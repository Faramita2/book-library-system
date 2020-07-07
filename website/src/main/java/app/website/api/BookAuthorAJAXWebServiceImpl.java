package app.website.api;

import app.api.website.BookAuthorAJAXWebService;
import app.api.website.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.website.bookauthor.SearchBookAuthorAJAXResponse;
import app.website.service.BookAuthorService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookAuthorAJAXWebServiceImpl implements BookAuthorAJAXWebService {
    @Inject
    BookAuthorService service;

    @Override
    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        return service.search(request);
    }
}
