package app.api.booksitefrontend;

import app.api.booksitefrontend.booktag.ListBookTagAJAXResponse;
import app.api.booksitefrontend.booktag.SearchBookTagAJAXRequest;
import app.api.booksitefrontend.booktag.SearchBookTagAJAXResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface BookTagAJAXWebService {
    @PUT
    @Path("/ajax/book-tag")
    SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request);

    @GET
    @Path("/ajax/book-tag")
    ListBookTagAJAXResponse list();
}
