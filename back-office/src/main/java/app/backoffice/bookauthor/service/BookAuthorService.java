package app.backoffice.bookauthor.service;

import app.api.backoffice.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.backoffice.bookauthor.UpdateBookAuthorAJAXRequest;
import app.book.api.BOAuthorWebService;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookAuthorService {
    @Inject
    BOAuthorWebService service;

    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        BOSearchAuthorRequest req = new BOSearchAuthorRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        BOSearchAuthorResponse resp = service.search(req);
        response.total = resp.total;
        response.authors = resp.authors.stream().map(a -> {
            SearchBookAuthorAJAXResponse.Author author = new SearchBookAuthorAJAXResponse.Author();
            author.id = a.id;
            author.name = a.name;

            return author;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookAuthorAJAXRequest request) {

    }

    public void update(Long id, UpdateBookAuthorAJAXRequest request) {

    }

    public void delete(Long id) {

    }
}
