package app.backoffice.book.service;

import app.api.backoffice.book.BookStatusAJAXView;
import app.api.backoffice.book.SearchBookAJAXRequest;
import app.api.backoffice.book.SearchBookAJAXResponse;
import app.book.api.BOBookWebService;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookService {
    @Inject
    BOBookWebService boBookWebService;

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        BOSearchBookRequest req = new BOSearchBookRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        req.tagIds = request.tagIds;
        req.description = request.description;
        req.categoryIds = request.categoryIds;
        req.authorIds = request.authorIds;
        BOSearchBookResponse resp = boBookWebService.search(req);
        SearchBookAJAXResponse response = new SearchBookAJAXResponse();
        response.total = resp.total;
        response.books = resp.books.stream().map(book -> {
            SearchBookAJAXResponse.Book b = new SearchBookAJAXResponse.Book();
            b.id = book.id;
            b.name = book.name;
            b.tagName = book.tagName;
            b.description = book.description;
            b.categoryName = book.categoryName;
            b.authorName = book.authorName;
            b.status = BookStatusAJAXView.valueOf(book.status.name());

            return b;
        }).collect(Collectors.toList());

        return response;
    }
}
