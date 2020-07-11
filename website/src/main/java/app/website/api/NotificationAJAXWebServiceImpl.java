package app.website.api;

import app.website.api.notification.DeleteBatchNotificationAJAXRequest;
import app.website.api.notification.GetNotificationAJAXResponse;
import app.website.api.notification.SearchNotificationAJAXRequest;
import app.website.api.notification.SearchNotificationAJAXResponse;
import app.website.service.NotificationService;
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
public class NotificationAJAXWebServiceImpl implements NotificationAJAXWebService {
    @Inject
    NotificationService service;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

    @Override
    public SearchNotificationAJAXResponse search(SearchNotificationAJAXRequest request) {
        return service.search(request, userId());
    }

    @Override
    public GetNotificationAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id, userId(), username());
    }

    @Override
    public void deleteBatch(DeleteBatchNotificationAJAXRequest request) {
        ActionLogContext.put("ids", request.ids);
        service.deleteBatch(request, username());
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
