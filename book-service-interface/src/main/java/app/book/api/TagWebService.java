package app.book.api;

import app.book.api.tag.ListTagResponse;
import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface TagWebService {
    @PUT
    @Path("/tag")
    SearchTagResponse search(SearchTagRequest request);

    @GET
    @Path("/tag")
    ListTagResponse list();
}
