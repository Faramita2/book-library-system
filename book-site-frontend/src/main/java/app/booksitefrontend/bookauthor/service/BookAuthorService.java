package app.booksitefrontend.bookauthor.service;

import app.api.booksitefrontend.bookauthor.ListBookAuthorAJAXResponse;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksitefrontend.bookauthor.SearchBookAuthorAJAXResponse;
import app.book.api.AuthorWebService;
import app.book.api.author.ListAuthorResponse;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.author.SearchAuthorResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookAuthorService {
    @Inject
    AuthorWebService authorWebService;

    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        SearchAuthorRequest req = new SearchAuthorRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        SearchAuthorResponse resp = authorWebService.search(req);
        response.total = resp.total;
        response.authors = resp.authors.stream().map(author -> {
            SearchBookAuthorAJAXResponse.Author view = new SearchBookAuthorAJAXResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public ListBookAuthorAJAXResponse list() {
        ListAuthorResponse listAuthorResponse = authorWebService.list();
        ListBookAuthorAJAXResponse response = new ListBookAuthorAJAXResponse();
        response.total = listAuthorResponse.total;
        response.authors = listAuthorResponse.authors.stream().map(author -> {
            ListBookAuthorAJAXResponse.Author view = new ListBookAuthorAJAXResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
