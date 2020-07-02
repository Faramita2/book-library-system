package app.booksitefrontend.booktag.web;

import app.api.booksitefrontend.BookTagAJAXWebService;
import app.api.booksitefrontend.booktag.ListBookTagAJAXResponse;
import app.api.booksitefrontend.booktag.SearchBookTagAJAXRequest;
import app.api.booksitefrontend.booktag.SearchBookTagAJAXResponse;
import app.booksitefrontend.booktag.service.BookTagService;
import app.booksitefrontend.user.web.UserPass;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookTagAJAXWebServiceImpl implements BookTagAJAXWebService {
    @Inject
    BookTagService service;

    @UserPass
    @Override
    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public ListBookTagAJAXResponse list() {
        return service.list();
    }
}
