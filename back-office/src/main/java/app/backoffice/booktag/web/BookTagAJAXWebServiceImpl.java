package app.backoffice.booktag.web;

import app.api.backoffice.BookTagAJAXWebService;
import app.api.backoffice.booktag.CreateBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.api.backoffice.booktag.UpdateBookTagAJAXRequest;
import app.backoffice.booktag.service.BookTagService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BookTagAJAXWebServiceImpl implements BookTagAJAXWebService {
    @Inject
    BookTagService service;

    @Override
    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookTagAJAXRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookTagAJAXRequest request) {

    }
}
