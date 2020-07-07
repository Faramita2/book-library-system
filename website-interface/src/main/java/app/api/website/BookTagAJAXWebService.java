package app.api.website;

import app.api.website.booktag.SearchBookTagAJAXRequest;
import app.api.website.booktag.SearchBookTagAJAXResponse;
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
