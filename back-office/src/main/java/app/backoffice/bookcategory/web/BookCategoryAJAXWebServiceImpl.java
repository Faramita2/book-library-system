package app.backoffice.bookcategory.web;

import app.api.backoffice.BookCategoryAJAXWebService;
import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.backoffice.bookcategory.UpdateBookCategoryAJAXRequest;
import app.backoffice.bookcategory.service.BookCategoryService;
import core.framework.inject.Inject;

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

    }

    @Override
    public void update(Long id, UpdateBookCategoryAJAXRequest request) {

    }
}
