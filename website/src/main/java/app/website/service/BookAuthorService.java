package app.website.service;

import app.website.api.bookauthor.SearchBookAuthorAJAXRequest;
import app.website.api.bookauthor.SearchBookAuthorAJAXResponse;
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
        SearchAuthorResponse searchAuthorResponse = authorWebService.search(searchAuthorRequest);

        SearchBookAuthorAJAXResponse response = new SearchBookAuthorAJAXResponse();
        response.total = searchAuthorResponse.total;
        response.authors = searchAuthorResponse.authors.stream().map(author -> {
            SearchBookAuthorAJAXResponse.Author view = new SearchBookAuthorAJAXResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
