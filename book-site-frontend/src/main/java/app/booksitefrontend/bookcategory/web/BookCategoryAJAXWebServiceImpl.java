package app.booksitefrontend.bookcategory.web;

import app.api.booksitefrontend.BookCategoryAJAXWebService;
import app.api.booksitefrontend.bookcategory.ListBookCategoryAJAXResponse;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXResponse;
import app.booksitefrontend.bookcategory.service.BookCategoryService;
import app.booksitefrontend.user.web.UserPass;
import core.framework.inject.Inject;


/**
 * @author zoo
 */
public class BookCategoryAJAXWebServiceImpl implements BookCategoryAJAXWebService {
    @Inject
    BookCategoryService service;

    @UserPass
    @Override
    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public ListBookCategoryAJAXResponse list() {
        return service.list();
    }
}
