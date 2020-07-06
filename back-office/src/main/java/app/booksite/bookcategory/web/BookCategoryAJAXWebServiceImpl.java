package app.booksite.bookcategory.web;

import app.api.backoffice.BookCategoryAJAXWebService;
import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.backoffice.bookcategory.UpdateBookCategoryAJAXRequest;
import app.booksite.bookcategory.service.BookCategoryService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BookCategoryAJAXWebServiceImpl implements BookCategoryAJAXWebService {
    @Inject
    BookCategoryService service;

    @Override
    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void create(CreateBookCategoryAJAXRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id);
    }
}
