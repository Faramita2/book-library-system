package app.booksite.service;

import app.api.backoffice.bookcategory.CreateBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXRequest;
import app.api.backoffice.bookcategory.SearchBookCategoryAJAXResponse;
import app.api.backoffice.bookcategory.UpdateBookCategoryAJAXRequest;
import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import app.book.api.category.BOUpdateCategoryRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookCategoryService {
    @Inject
    BOCategoryWebService boCategoryWebService;
    @Inject
    WebContext webContext;

    public SearchBookCategoryAJAXResponse search(SearchBookCategoryAJAXRequest request) {
        BOSearchCategoryRequest boSearchCategoryRequest = new BOSearchCategoryRequest();
        boSearchCategoryRequest.skip = request.skip;
        boSearchCategoryRequest.limit = request.limit;
        boSearchCategoryRequest.name = request.name;
        BOSearchCategoryResponse boSearchCategoryResponse = boCategoryWebService.search(boSearchCategoryRequest);

        SearchBookCategoryAJAXResponse response = new SearchBookCategoryAJAXResponse();
        response.total = boSearchCategoryResponse.total;
        response.categories = boSearchCategoryResponse.categories.stream().map(category -> {
            SearchBookCategoryAJAXResponse.Category view = new SearchBookCategoryAJAXResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookCategoryAJAXRequest request) {
        BOCreateCategoryRequest boCreateCategoryRequest = new BOCreateCategoryRequest();
        boCreateCategoryRequest.name = request.name;
        boCreateCategoryRequest.requestedBy = adminAccount();

        boCategoryWebService.create(boCreateCategoryRequest);
    }

    public void update(Long id, UpdateBookCategoryAJAXRequest request) {
        BOUpdateCategoryRequest boUpdateCategoryRequest = new BOUpdateCategoryRequest();
        boUpdateCategoryRequest.name = request.name;
        boUpdateCategoryRequest.requestedBy = adminAccount();

        boCategoryWebService.update(id, boUpdateCategoryRequest);
    }

    public void delete(Long id) {
        boCategoryWebService.delete(id);
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
