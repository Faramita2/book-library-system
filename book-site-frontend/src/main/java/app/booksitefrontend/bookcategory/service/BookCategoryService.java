package app.booksitefrontend.bookcategory.service;

import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXResponse;
import app.book.api.CategoryWebService;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.category.SearchCategoryResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookCategoryService {
    @Inject
    CategoryWebService categoryWebService;

    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        SearchCategoryRequest req = new SearchCategoryRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        SearchCategoryResponse resp = categoryWebService.search(req);
        SearchBookCategoryAJAXResponse response = new SearchBookCategoryAJAXResponse();
        response.total = resp.total;
        response.categories = resp.categories.stream().map(category -> {
            SearchBookCategoryAJAXResponse.Category view = new SearchBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
