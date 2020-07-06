package app.booksite.booktag.web;

import app.api.backoffice.BookTagAJAXWebService;
import app.api.backoffice.booktag.CreateBookTagAJAXRequest;
import app.api.backoffice.booktag.ListBookTagAJAXResponse;
import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.api.backoffice.booktag.UpdateBookTagAJAXRequest;
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
        ActionLogContext.put("id", id);
        service.update(id, request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id);
    }
}
