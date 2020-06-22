package app.book.category.service;

import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import app.book.api.category.BOUpdateCategoryRequest;
import app.book.category.domain.Category;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOCategoryService {
    @Inject
    Repository<Category> repository;

    public void create(BOCreateCategoryRequest request) {
        Category category = new Category();
        category.name = request.name;
        category.createdAt = LocalDateTime.now();
        category.updatedAt = LocalDateTime.now();
        category.createdBy = "BookService";
        category.updatedBy = "BookService";

        repository.insert(category).orElseThrow();
    }

    public BOSearchCategoryResponse search(BOSearchCategoryRequest request) {
        Query<Category> query = repository.select();

        if (request.name != null) {
            query.where("name like ?%", request.name);
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchCategoryResponse response = new BOSearchCategoryResponse();
        response.total = query.count();
        response.categories = query.fetch().parallelStream().map(a -> {
            BOSearchCategoryResponse.Category category = new BOSearchCategoryResponse.Category();
            category.id = a.id;
            category.name = a.name;

            return category;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateCategoryRequest request) {
        Category category = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("category not found, id = {}", id)));
        category.name = request.name;
        repository.partialUpdate(category);
    }

    public void delete(Long id) {
        repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("category not found, id = {}", id)));
        repository.delete(id);
    }
}
