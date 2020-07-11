package app.website.api;

import app.website.service.BorrowRecordService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.CookieSpec;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.Map;

/**
 * @author meow
 */
public class BorrowRecordAJAXWebServiceImpl implements BorrowRecordAJAXWebService {
    @Inject
    BorrowRecordService service;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

    @Override
    public void returnBook(String id) {
        ActionLogContext.put("id", id);
        Long userId = userId();
        String username = username();
        service.returnBook(id, userId, username);
    }

    private Long userId() {
        String userId = session().get("user_id");
        if (userId == null) {
            throw new UnauthorizedException("please login first.");
        }
        return Long.valueOf(userId);
    }

    private String username() {
        String username = session().get("username");
        if (username == null) {
            throw new UnauthorizedException("please login first.");
        }
        return username;
    }

    private Map<String, String> session() {
        String sessionId = webContext.request().cookie(new CookieSpec("SessionId")).orElse(null);
        return redis.hash().getAll(Strings.format("session:{}", sessionId));
    }
}
