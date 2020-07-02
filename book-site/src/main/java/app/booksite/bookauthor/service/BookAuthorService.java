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
        BOSearchAuthorRequest boSearchAuthorRequest = new BOSearchAuthorRequest();
        boSearchAuthorRequest.skip = request.skip;
        boSearchAuthorRequest.limit = request.limit;
        boSearchAuthorRequest.name = request.name;

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        BOSearchAuthorResponse boSearchAuthorResponse = boAuthorWebService.search(boSearchAuthorRequest);
        response.total = boSearchAuthorResponse.total;
        response.authors = boSearchAuthorResponse.authors.stream().map(author -> {
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
        BOCreateAuthorRequest boCreateAuthorRequest = new BOCreateAuthorRequest();
        boCreateAuthorRequest.name = request.name;
        boCreateAuthorRequest.operator = "book-site";
        boAuthorWebService.create(boCreateAuthorRequest);
    }

    public void update(Long id, UpdateBookAuthorAJAXRequest request) {
        BOUpdateAuthorRequest boUpdateAuthorRequest = new BOUpdateAuthorRequest();
        boUpdateAuthorRequest.name = request.name;
        boUpdateAuthorRequest.operator = "book-site";
        boAuthorWebService.update(id, boUpdateAuthorRequest);
    }

    public void delete(Long id) {
        boAuthorWebService.delete(id);
    }
}
