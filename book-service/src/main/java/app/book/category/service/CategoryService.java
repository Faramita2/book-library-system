package app.book.category.service;

import app.book.api.category.SearchCategoryRequest;
import app.book.api.category.SearchCategoryResponse;
import app.book.category.domain.Category;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

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
        if (request.name != null) {
            //TODO
            query.where("`name` like ?", request.name + "%");
        }

        response.total = query.count();
        response.categories = query.fetch().parallelStream().map(a -> {
            SearchCategoryResponse.Category category = new SearchCategoryResponse.Category();
            category.id = a.id;
            category.name = a.name;

            return category;
        }).collect(Collectors.toList());

        return response;
    }
}
