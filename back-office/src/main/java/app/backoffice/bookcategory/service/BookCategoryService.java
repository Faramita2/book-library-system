package app.backoffice.bookcategory.service;

import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookCategoryService {
    @Inject
    BOCategoryWebService boCategoryWebService;

    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        BOSearchCategoryRequest req = new BOSearchCategoryRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        BOSearchCategoryResponse resp = boCategoryWebService.search(req);
        SearchBookCategoryAJAXResponse response = new SearchBookCategoryAJAXResponse();
        response.total = resp.total;
        if (resp.categories != null){
            response.categories = resp.categories.stream().map(category -> {
                SearchBookCategoryAJAXResponse.Category c = new SearchBookCategoryAJAXResponse.Category();
                c.id = category.id;
                c.name = category.name;

                return c;
            }).collect(Collectors.toList());
        }

        return response;
    }

    public void create(CreateBookCategoryAJAXRequest request) {
        BOCreateCategoryRequest req = new BOCreateCategoryRequest();
        req.name = request.name;
        boCategoryWebService.create(req);
    }
}
