package app.website.bookauthor.service;

import app.api.website.bookauthor.ListBookAuthorAJAXResponse;
import app.api.website.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.website.bookauthor.SearchBookAuthorAJAXResponse;
import app.book.api.AuthorWebService;
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
        SearchAuthorRequest searchAuthorRequest = new SearchAuthorRequest();
        searchAuthorRequest.skip = request.skip;
        searchAuthorRequest.limit = request.limit;
        searchAuthorRequest.name = request.name;

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        SearchAuthorResponse searchAuthorResponse = authorWebService.search(searchAuthorRequest);
        response.total = searchAuthorResponse.total;
        response.authors = searchAuthorResponse.authors.stream().map(author -> {
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
