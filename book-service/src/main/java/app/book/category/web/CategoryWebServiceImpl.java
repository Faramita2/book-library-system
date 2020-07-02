package app.book.category.web;

import app.book.api.CategoryWebService;
import app.book.api.category.ListCategoryResponse;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.category.SearchCategoryResponse;
import app.book.category.service.CategoryService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class CategoryWebServiceImpl implements CategoryWebService {
    @Inject
    CategoryService service;

    @Override
    public SearchCategoryResponse search(SearchCategoryRequest request) {
        return service.search(request);
    }

    @Override
    public ListCategoryResponse list() {
        return service.list();
    }
}
