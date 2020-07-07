package app.booksite.bookauthor.service;

import app.api.backoffice.bookauthor.CreateBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXRequest;
import app.api.backoffice.bookauthor.SearchBookAuthorAJAXResponse;
import app.api.backoffice.bookauthor.UpdateBookAuthorAJAXRequest;
import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookAuthorService {
    @Inject
    BOAuthorWebService boAuthorWebService;
    @Inject
    WebContext webContext;

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

    public void create(CreateBookAuthorAJAXRequest request) {
        BOCreateAuthorRequest boCreateAuthorRequest = new BOCreateAuthorRequest();
        boCreateAuthorRequest.name = request.name;
        boCreateAuthorRequest.requestedBy = adminAccount();
        boAuthorWebService.create(boCreateAuthorRequest);
    }

    public void update(Long id, UpdateBookAuthorAJAXRequest request) {
        BOUpdateAuthorRequest boUpdateAuthorRequest = new BOUpdateAuthorRequest();
        boUpdateAuthorRequest.name = request.name;
        boUpdateAuthorRequest.requestedBy = adminAccount();
        boAuthorWebService.update(id, boUpdateAuthorRequest);
    }

    public void delete(Long id) {
        boAuthorWebService.delete(id);
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
