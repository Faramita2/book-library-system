package app.book.category.service;

import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOListCategoryResponse;
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
        LocalDateTime now = LocalDateTime.now();
        category.createdAt = now;
        category.updatedAt = now;
        category.createdBy = request.operator;
        category.updatedBy = request.operator;

        repository.insert(category).orElseThrow();
    }

    public BOSearchCategoryResponse search(BOSearchCategoryRequest request) {
        Query<Category> query = repository.select();

        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchCategoryResponse response = new BOSearchCategoryResponse();
        response.total = query.count();
        response.categories = query.fetch().stream().map(category -> {
            BOSearchCategoryResponse.Category view = new BOSearchCategoryResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public BOListCategoryResponse list() {
        BOListCategoryResponse response = new BOListCategoryResponse();
        Query<Category> query = repository.select();
        response.total = query.count();
        response.categories = query.fetch().stream().map(category -> {
            BOListCategoryResponse.Category view = new BOListCategoryResponse.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateCategoryRequest request) {
        Category category = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("category not found, id = {}", id), "BOOK_CATEGORY_NOT_FOUND"));
        category.name = request.name;
        repository.partialUpdate(category);
    }

    public void delete(Long id) {
        repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("category not found, id = {}", id), "BOOK_CATEGORY_NOT_FOUND"));
        repository.delete(id);
    }
}
