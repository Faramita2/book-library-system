package app.backoffice.api;

import app.api.backoffice.BookTagAJAXWebService;
import app.api.backoffice.booktag.CreateBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.api.backoffice.booktag.UpdateBookTagAJAXRequest;
import app.backoffice.service.BookTagService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author zoo
 */
public class BookTagAJAXWebServiceImpl implements BookTagAJAXWebService {
    @Inject
    BookTagService service;
    @Inject
    WebContext webContext;

    @Override
    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookTagAJAXRequest request) {
        service.create(request, adminAccount());
    }

    @Override
    public void update(Long id, UpdateBookTagAJAXRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request, adminAccount());
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id);
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
