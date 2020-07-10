package app.website.api;

import app.website.api.booktag.SearchBookTagAJAXRequest;
import app.website.api.booktag.SearchBookTagAJAXResponse;
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
