package app.book.author.web;

import app.book.api.AuthorWebService;
import app.book.api.author.ListAuthorResponse;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.author.SearchAuthorResponse;
import app.book.author.service.AuthorService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class AuthorWebServiceImpl implements AuthorWebService {
    @Inject
    AuthorService service;

    @Override
    public SearchAuthorResponse search(SearchAuthorRequest request) {
        return service.search(request);
    }

    @Override
    public ListAuthorResponse list() {
        return service.list();
    }
}
