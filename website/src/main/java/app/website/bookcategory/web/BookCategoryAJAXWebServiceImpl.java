package app.website.bookcategory.web;

import app.api.website.BookCategoryAJAXWebService;
import app.api.website.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.website.bookcategory.SearchBookCategoryAJAXResponse;
import app.website.bookcategory.service.BookCategoryService;
import app.website.user.web.SkipLogin;
import core.framework.inject.Inject;


/**
 * @author zoo
 */
public class BookCategoryAJAXWebServiceImpl implements BookCategoryAJAXWebService {
    @Inject
    BookCategoryService service;

    @SkipLogin
    @Override
    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        return service.search(request);
    }
}
