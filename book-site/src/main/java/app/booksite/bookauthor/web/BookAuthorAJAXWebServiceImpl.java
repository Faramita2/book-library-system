package app.booksite.bookauthor.web;

import app.api.booksite.BookAuthorAJAXWebService;
import app.api.booksite.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.booksite.bookauthor.ListBookAuthorAJAXResponse;
import app.api.booksite.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksite.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.booksite.bookauthor.UpdateBookAuthorAJAXRequest;
import app.booksite.bookauthor.service.BookAuthorService;
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
    public ListBookAuthorAJAXResponse list() {
        return service.list();
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
