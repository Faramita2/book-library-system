package app.booksite.bookcategory.service;

import app.api.booksite.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.UpdateBookCategoryAJAXRequest;
import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import app.book.api.category.BOUpdateCategoryRequest;
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
        response.categories = resp.categories.stream().map(category -> {
            SearchBookCategoryAJAXResponse.Category view = new SearchBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookCategoryAJAXRequest request) {
        BOCreateCategoryRequest req = new BOCreateCategoryRequest();
        req.name = request.name;
        boCategoryWebService.create(req);
    }

    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
        BOUpdateCategoryRequest req = new BOUpdateCategoryRequest();
        req.name = request.name;
        boCategoryWebService.update(id, req);
    }
}
