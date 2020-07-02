package app.booksite.bookcategory.service;

import app.api.booksite.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.ListBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksite.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.booksite.bookcategory.UpdateBookCategoryAJAXRequest;
import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOListCategoryResponse;
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

    public ListBookCategoryAJAXResponse list() {
        ListBookCategoryAJAXResponse response = new ListBookCategoryAJAXResponse();
        BOListCategoryResponse listCategoryResponse = boCategoryWebService.list();
        response.total = listCategoryResponse.total;
        response.categories = listCategoryResponse.categories.stream().map(category -> {
            ListBookCategoryAJAXResponse.Category view = new ListBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookCategoryAJAXRequest request) {
        BOCreateCategoryRequest req = new BOCreateCategoryRequest();
        req.name = request.name;
        req.operator = "book-site";
        boCategoryWebService.create(req);
    }

    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
        BOUpdateCategoryRequest req = new BOUpdateCategoryRequest();
        req.name = request.name;
        req.operator = "book-site";
        boCategoryWebService.update(id, req);
    }
}
