package app.book.api;

import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface TagWebService {
    @PUT
    @Path("/tag")
    SearchTagResponse search(SearchTagRequest request);
}
