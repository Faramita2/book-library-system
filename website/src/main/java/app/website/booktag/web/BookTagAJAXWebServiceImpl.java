package app.website.booktag.web;

import app.api.website.BookTagAJAXWebService;
import app.api.website.booktag.ListBookTagAJAXResponse;
import app.api.website.booktag.SearchBookTagAJAXRequest;
import app.api.website.booktag.SearchBookTagAJAXResponse;
import app.website.booktag.service.BookTagService;
import app.website.user.web.SkipLogin;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookTagAJAXWebServiceImpl implements BookTagAJAXWebService {
    @Inject
    BookTagService service;

    @SkipLogin
    @Override
    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public ListBookTagAJAXResponse list() {
        return service.list();
    }
}
