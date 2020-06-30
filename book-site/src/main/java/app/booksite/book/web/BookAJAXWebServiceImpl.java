package app.booksite.book.web;

import app.api.booksite.BookAJAXWebService;
import app.api.booksite.book.CreateBookAJAXRequest;
import app.api.booksite.book.GetBookAJAXResponse;
import app.api.booksite.book.SearchBookAJAXRequest;
import app.api.booksite.book.SearchBookAJAXResponse;
import app.api.booksite.book.UpdateBookAJAXRequest;
import app.booksite.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;

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
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookAJAXRequest request) {
        ActionLogContext.put("book_id", id);
        service.update(id, request);
    }
}
