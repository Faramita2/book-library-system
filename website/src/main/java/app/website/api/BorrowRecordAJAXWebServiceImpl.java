package app.website.api;

import app.api.website.BorrowRecordAJAXWebService;
import app.website.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class BorrowRecordAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;
    @Inject
    WebContext webContext;

    @Override
    public void returnBook(String id) {
        ActionLogContext.put("id", id);
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        String username = webContext.request().session().get("username").orElseThrow(() -> new UnauthorizedException("please login first."));
        service.returnBook(id, Long.valueOf(userId), username);
    }
}
