package app.book.author.web;

import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import app.book.author.service.BOAuthorService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOAuthorWebServiceImpl implements BOAuthorWebService {
    @Inject
    BOAuthorService service;

    @Override
    public BOSearchAuthorResponse search(BOSearchAuthorRequest request) {
        return null;
    }

    @Override
    public void create(BOCreateAuthorRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, BOUpdateAuthorRequest request) {

    }

    @Override
    public void delete(Long id) {

    }
}