package app.book.category.web;

import app.book.api.BOCategoryWebService;
import app.book.api.category.BOCreateCategoryRequest;
import app.book.api.category.BOListCategoryResponse;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.category.BOSearchCategoryResponse;
import app.book.api.category.BOUpdateCategoryRequest;
import app.book.category.service.BOCategoryService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BOCategoryWebServiceImpl implements BOCategoryWebService {
    @Inject
    BOCategoryService service;

    @Override
    public BOSearchCategoryResponse search(BOSearchCategoryRequest request) {
        return service.search(request);
    }

    @Override
    public BOListCategoryResponse list() {
        return service.list();
    }

    @Override
    public void create(BOCreateCategoryRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, BOUpdateCategoryRequest request) {
        ActionLogContext.put("category_id", id);
        service.update(id, request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("category_id", id);
        service.delete(id);
    }
}
