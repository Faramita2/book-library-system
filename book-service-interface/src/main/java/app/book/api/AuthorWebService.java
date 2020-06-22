package app.book.api;

import app.book.api.author.SearchAuthorRequest;
import app.book.api.author.SearchAuthorResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author zoo
 */
public interface AuthorWebService {
    @PUT
    @Path("/author")
    SearchAuthorResponse search(SearchAuthorRequest request);
}
