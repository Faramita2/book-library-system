package app.booksitefrontend.bookcategory.service;

import app.api.booksitefrontend.bookcategory.ListBookCategoryAJAXResponse;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.booksitefrontend.bookcategory.SearchBookCategoryAJAXResponse;
import app.book.api.CategoryWebService;
import app.book.api.category.ListCategoryResponse;
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
        SearchCategoryRequest searchCategoryRequest = new SearchCategoryRequest();
        searchCategoryRequest.skip = request.skip;
        searchCategoryRequest.limit = request.limit;
        searchCategoryRequest.name = request.name;
        SearchCategoryResponse searchCategoryResponse = categoryWebService.search(searchCategoryRequest);
        SearchBookCategoryAJAXResponse response = new SearchBookCategoryAJAXResponse();
        response.total = searchCategoryResponse.total;
        response.categories = searchCategoryResponse.categories.stream().map(category -> {
            SearchBookCategoryAJAXResponse.Category view = new SearchBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public ListBookCategoryAJAXResponse list() {
        ListBookCategoryAJAXResponse response = new ListBookCategoryAJAXResponse();
        ListCategoryResponse listCategoryResponse = categoryWebService.list();
        response.total = listCategoryResponse.total;
        response.categories = listCategoryResponse.categories.stream().map(category -> {
            ListBookCategoryAJAXResponse.Category view = new ListBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
