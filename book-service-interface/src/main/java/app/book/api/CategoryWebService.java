package app.book.api;

import app.book.api.category.SearchCategoryRequest;
import app.book.api.category.SearchCategoryResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface CategoryWebService {
    @PUT
    @Path("/category")
    SearchCategoryResponse search(SearchCategoryRequest request);
}
