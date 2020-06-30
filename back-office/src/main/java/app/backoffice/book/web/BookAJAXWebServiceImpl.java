package app.backoffice.book.web;

import app.api.backoffice.BookAJAXWebService;
import app.api.backoffice.book.CreateBookAJAXRequest;
import app.api.backoffice.book.GetBookAJAXResponse;
import app.api.backoffice.book.SearchBookAJAXRequest;
import app.api.backoffice.book.SearchBookAJAXResponse;
import app.api.backoffice.book.UpdateBookAJAXRequest;
import app.backoffice.book.service.BookService;
import core.framework.inject.Inject;

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

    }
}
