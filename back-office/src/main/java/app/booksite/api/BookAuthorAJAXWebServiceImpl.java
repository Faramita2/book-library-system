package app.booksite.api;

import app.api.backoffice.BookAuthorAJAXWebService;
import app.api.backoffice.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.backoffice.bookauthor.UpdateBookAuthorAJAXRequest;
import app.booksite.service.BookAuthorService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author zoo
 */
public class BookAuthorAJAXWebServiceImpl implements BookAuthorAJAXWebService {
    @Inject
    BookAuthorService service;
    @Inject
    WebContext webContext;

    @Override
    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookAuthorAJAXRequest request) {
        String adminAccount = adminAccount();
        service.create(request, adminAccount);
    }

    @Override
    public void update(Long id, UpdateBookAuthorAJAXRequest request) {
        ActionLogContext.put("id", id);

        String adminAccount = adminAccount();

        service.update(id, request, adminAccount);
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
