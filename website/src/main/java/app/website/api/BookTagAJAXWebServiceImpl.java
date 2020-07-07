package app.website.api;

import app.api.website.BookTagAJAXWebService;
import app.api.website.booktag.SearchBookTagAJAXRequest;
import app.api.website.booktag.SearchBookTagAJAXResponse;
import app.website.service.BookTagService;
import app.website.web.interceptor.SkipLogin;
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
}
