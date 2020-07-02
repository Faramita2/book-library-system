package app.booksitefrontend.bookauthor.web;

import app.api.booksitefrontend.BookAuthorAJAXWebService;
import app.api.booksitefrontend.bookauthor.ListBookAuthorAJAXResponse;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXResponse;
import app.booksitefrontend.bookauthor.service.BookAuthorService;
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

    @Override
    public ListBookAuthorAJAXResponse list() {
        return service.list();
    }
}
