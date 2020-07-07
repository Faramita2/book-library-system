package app.booksite.api;

import app.api.backoffice.BookAJAXWebService;
import app.api.backoffice.book.CreateBookAJAXRequest;
import app.api.backoffice.book.GetBookAJAXResponse;
import app.api.backoffice.book.SearchBookAJAXRequest;
import app.api.backoffice.book.SearchBookAJAXResponse;
import app.api.backoffice.book.UpdateBookAJAXRequest;
import app.booksite.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author zoo
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;
    @Inject
    WebContext webContext;

    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public GetBookAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void create(CreateBookAJAXRequest request) {
        service.create(request, adminAccount());
    }

    @Override
    public void update(Long id, UpdateBookAJAXRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request, adminAccount());
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
