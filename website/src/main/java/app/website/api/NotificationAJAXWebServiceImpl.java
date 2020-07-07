package app.website.api;

import app.api.website.NotificationAJAXWebService;
import app.api.website.notification.DeleteBatchNotificationAJAXRequest;
import app.api.website.notification.GetNotificationAJAXResponse;
import app.api.website.notification.SearchNotificationAJAXRequest;
import app.api.website.notification.SearchNotificationAJAXResponse;
import app.website.service.NotificationService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class NotificationAJAXWebServiceImpl implements NotificationAJAXWebService {
    @Inject
    NotificationService service;
    @Inject
    WebContext webContext;

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
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return Long.valueOf(userId);
    }

    private String username() {
        return webContext.request().session().get("username").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
