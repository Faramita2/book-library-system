package app.website.api;

import app.website.api.booktag.SearchBookTagAJAXRequest;
import app.website.api.booktag.SearchBookTagAJAXResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BookTagAJAXWebService {
    @PUT
    @Path("/ajax/book-tag")
    SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request);
}
