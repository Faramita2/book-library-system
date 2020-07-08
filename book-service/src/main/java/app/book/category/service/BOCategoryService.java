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
    Repository<Category> categoryRepository;

    public void create(BOCreateCategoryRequest request) {
        Category category = new Category();
        category.name = request.name;
        LocalDateTime now = LocalDateTime.now();
        category.createdTime = now;
        category.updatedTime = now;
        category.createdBy = request.requestedBy;
        category.updatedBy = request.requestedBy;

        categoryRepository.insert(category);
    }

    public BOSearchCategoryResponse search(BOSearchCategoryRequest request) {
        Query<Category> query = categoryRepository.select();

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

    public void update(Long id, BOUpdateCategoryRequest request) {
        Category category = categoryRepository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("category not found, id = {}", id), "BOOK_CATEGORY_NOT_FOUND"));
        category.name = request.name;
        category.updatedBy = request.requestedBy;
        category.updatedTime = LocalDateTime.now();

        categoryRepository.partialUpdate(category);
    }

    public void delete(Long id) {
        categoryRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("category not found, id = {}", id), "BOOK_CATEGORY_NOT_FOUND"));
        categoryRepository.delete(id);
    }
}
