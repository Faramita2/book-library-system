package app.book.tag.web;

import app.book.api.BOTagWebService;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
import app.book.tag.service.BOTagService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BOTagWebServiceImpl implements BOTagWebService {
    @Inject
    BOTagService service;

    @Override
    public BOSearchTagResponse search(BOSearchTagRequest request) {
        return service.search(request);
    }

    @Override
    public BOListTagResponse list() {
        return service.list();
    }

    @Override
    public void create(BOCreateTagRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, BOUpdateTagRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id);
    }
}
