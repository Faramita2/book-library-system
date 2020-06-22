package app.book.book.web;

import app.book.api.BOBookWebService;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.book.service.BOBookService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOBookWebServiceImpl implements BOBookWebService {
    @Inject
    BOBookService service;

    @Override
    public BOSearchBookResponse search(BOSearchBookRequest request) {
        return service.search(request);
    }

    @Override
    public BOGetBookResponse get(Long id) {
        return null;
    }

    @Override
    public void create(BOCreateBookRequest request) {
        service.create(request);
    }

    @Override
    public void update(Long id, BOUpdateBookRequest request) {

    }
}
