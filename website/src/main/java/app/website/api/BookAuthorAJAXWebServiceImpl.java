package app.website.api;

import app.website.api.bookauthor.SearchBookAuthorAJAXRequest;
import app.website.api.bookauthor.SearchBookAuthorAJAXResponse;
import app.website.service.BookAuthorService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookAuthorAJAXWebServiceImpl implements BookAuthorAJAXWebService {
    @Inject
    BookAuthorService service;

    @SkipLogin
    @Override
    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        return service.search(request);
    }
}
