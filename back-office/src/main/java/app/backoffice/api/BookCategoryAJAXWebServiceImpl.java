package app.backoffice.api;

import app.api.backoffice.BookCategoryAJAXWebService;
import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.backoffice.bookcategory.UpdateBookCategoryAJAXRequest;
import app.backoffice.service.BookCategoryService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author zoo
 */
public class BookCategoryAJAXWebServiceImpl implements BookCategoryAJAXWebService {
    @Inject
    BookCategoryService service;
    @Inject
    WebContext webContext;

    @Override
    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookCategoryAJAXRequest request) {
        service.create(request, adminAccount());
    }

    @Override
    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
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
