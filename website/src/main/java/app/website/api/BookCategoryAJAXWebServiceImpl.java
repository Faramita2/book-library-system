package app.website.api;

import app.website.api.bookcategory.SearchBookCategoryAJAXRequest;
import app.website.api.bookcategory.SearchBookCategoryAJAXResponse;
import app.website.service.BookCategoryService;
import app.website.web.interceptor.SkipLogin;
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
