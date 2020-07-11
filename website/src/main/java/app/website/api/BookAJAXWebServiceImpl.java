package app.website.api;

import app.website.api.book.GetBookAJAXResponse;
import app.website.api.book.SearchBookAJAXRequest;
import app.website.api.book.SearchBookAJAXResponse;
import app.website.api.book.SearchBorrowedBookAJAXRequest;
import app.website.api.book.SearchBorrowedBookAJAXResponse;
import app.website.api.borrowrecord.BorrowBookAJAXRequest;
import app.website.service.BookService;
import app.website.web.interceptor.SkipLogin;
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
public class BookAJAXWebServiceImpl implements BookAJAXWebService {
    @Inject
    BookService service;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

    @SkipLogin
    @Override
    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        return service.search(request);
    }

    @SkipLogin
    @Override
    public GetBookAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void borrow(Long id, BorrowBookAJAXRequest request) {
        ActionLogContext.put("id", id);
        service.borrow(id, request, userId(), username());
    }

    @Override
    public SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request) {
        return service.searchBorrowedBook(request, userId());
    }

    private Long userId() {
        String userId = session().get("user_id");
        if (Strings.isBlank(userId)) {
            throw new UnauthorizedException("please login first.");
        }
        return Long.valueOf(userId);
    }

    private String username() {
        String username = session().get("username");
        if (Strings.isBlank(username)) {
            throw new UnauthorizedException("please login first.");
        }
        return username;
    }

    private Map<String, String> session() {
        String sessionId = webContext.request().cookie(new CookieSpec("SessionId")).orElse(null);
        return redis.hash().getAll(Strings.format("session:{}", sessionId));
    }
}
