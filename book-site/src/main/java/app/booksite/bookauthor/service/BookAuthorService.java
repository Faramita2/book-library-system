package app.booksite.bookauthor.service;

import app.api.booksite.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.booksite.bookauthor.ListBookAuthorAJAXResponse;
import app.api.booksite.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.booksite.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.booksite.bookauthor.UpdateBookAuthorAJAXRequest;
import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOListAuthorResponse;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookAuthorService {
    @Inject
    BOAuthorWebService boAuthorWebService;

    public SearchBookAuthorAJAXResponse search(SearchBookAuthorAJAXRequest request) {
        BOSearchAuthorRequest req = new BOSearchAuthorRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        BOSearchAuthorResponse resp = boAuthorWebService.search(req);
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
        BOListAuthorResponse listAuthorResponse = boAuthorWebService.list();
        ListBookAuthorAJAXResponse response = new ListBookAuthorAJAXResponse();
        response.total = listAuthorResponse.total;
        response.authors = listAuthorResponse.authors.stream().map(author -> {
            ListBookAuthorAJAXResponse.Author view = new ListBookAuthorAJAXResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());

        return response;    }

    public void create(CreateBookAuthorAJAXRequest request) {
        BOCreateAuthorRequest req = new BOCreateAuthorRequest();
        req.name = request.name;
        req.operator = "book-site";
        boAuthorWebService.create(req);
    }

    public void update(Long id, UpdateBookAuthorAJAXRequest request) {
        BOUpdateAuthorRequest req = new BOUpdateAuthorRequest();
        req.name = request.name;
        req.operator = "book-site";
        boAuthorWebService.update(id, req);
    }
}
