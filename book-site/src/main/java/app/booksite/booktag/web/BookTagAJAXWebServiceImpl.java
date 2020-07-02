package app.booksite.booktag.web;

import app.api.booksite.BookTagAJAXWebService;
import app.api.booksite.booktag.CreateBookTagAJAXRequest;
import app.api.booksite.booktag.ListBookTagAJAXResponse;
import app.api.booksite.booktag.SearchBookTagAJAXRequest;
import app.api.booksite.booktag.SearchBookTagAJAXResponse;
import app.api.booksite.booktag.UpdateBookTagAJAXRequest;
import app.booksite.booktag.service.BookTagService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

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
    public ListBookTagAJAXResponse list() {
        return service.list();
    }

    @Override
    public void create(CreateBookTagAJAXRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookTagAJAXRequest request) {
        ActionLogContext.put("tag_id", id);
        service.update(id, request);
    }
}
