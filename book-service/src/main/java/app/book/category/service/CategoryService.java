package app.book.category.service;

import app.book.api.category.ListCategoryResponse;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.category.SearchCategoryResponse;
import app.book.category.domain.Category;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class CategoryService {
    @Inject
    Repository<Category> repository;

    public SearchCategoryResponse search(SearchCategoryRequest request) {
        SearchCategoryResponse response = new SearchCategoryResponse();
        Query<Category> query = repository.select();
        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        response.total = query.count();
        response.categories = query.fetch().stream().map(category -> {
            SearchCategoryResponse.Category view = new SearchCategoryResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public ListCategoryResponse list() {
        ListCategoryResponse response = new ListCategoryResponse();
        Query<Category> query = repository.select();
        response.total = query.count();
        response.categories = query.fetch().stream().map(category -> {
            ListCategoryResponse.Category view = new ListCategoryResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
