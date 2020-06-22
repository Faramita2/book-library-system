package app.book.author.web;

import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import app.book.author.service.BOAuthorService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BOAuthorWebServiceImpl implements BOAuthorWebService {
    @Inject
    BOAuthorService service;

    @Override
    public BOSearchAuthorResponse search(BOSearchAuthorRequest request) {
        return service.search(request);
    }

    @Override
    public void create(BOCreateAuthorRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, BOUpdateAuthorRequest request) {
        ActionLogContext.put("author_id", id);
        service.update(id, request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("author_id", id);
        service.delete(id);
    }
}
