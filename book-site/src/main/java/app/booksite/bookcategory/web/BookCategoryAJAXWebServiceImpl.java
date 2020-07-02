package app.booksite.bookcategory.web;

import app.api.booksite.BookCategoryAJAXWebService;
import app.api.booksite.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.ListBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.UpdateBookCategoryAJAXRequest;
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
    public ListBookCategoryAJAXResponse list() {
        return service.list();
    }

    @Override
    public void create(CreateBookCategoryAJAXRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
        ActionLogContext.put("category_id", id);
        service.update(id, request);
    }
}
