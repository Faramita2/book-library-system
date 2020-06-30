package app.backoffice.bookauthor.web;

import app.api.backoffice.BookAuthorAJAXWebService;
import app.api.backoffice.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.backoffice.bookauthor.UpdateBookAuthorAJAXRequest;
import app.backoffice.bookauthor.service.BookAuthorService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BookAuthorAJAXWebServiceImpl implements BookAuthorAJAXWebService {
    @Inject
    BookAuthorService service;

    @Override
    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookAuthorAJAXRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookAuthorAJAXRequest request) {
        ActionLogContext.put("author_id", id);
        service.update(id, request);
    }
}
