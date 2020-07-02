package app.booksitefrontend.notification.service;

import app.api.booksitefrontend.notification.DeleteBatchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXResponse;
import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class NotificationService {
    @Inject
    NotificationWebService notificationWebService;
    @Inject
    WebContext webContext;

    public SearchNotificationAJAXResponse search(SearchNotificationAJAXRequest request) {
        SearchNotificationRequest req = new SearchNotificationRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow(() -> new UnauthorizedException("please login first."));
        req.userId = Long.valueOf(userId.get());
        SearchNotificationResponse resp = notificationWebService.search(req);

        SearchNotificationAJAXResponse response = new SearchNotificationAJAXResponse();
        response.total = resp.total;
        response.notifications = resp.notifications.stream().map(notification -> {
            SearchNotificationAJAXResponse.Notification view = new SearchNotificationAJAXResponse.Notification();
            view.id = notification.id;
            view.content = notification.content;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void delete(Long id) {
        DeleteNotificationRequest req = new DeleteNotificationRequest();
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow(() -> new UnauthorizedException("please login first."));
        req.userId = Long.valueOf(userId.get());
        req.operator = "book-site-frontend";
        notificationWebService.delete(id, req);
    }

    public void deleteBatch(DeleteBatchNotificationAJAXRequest request) {
        DeleteBatchNotificationRequest req = new DeleteBatchNotificationRequest();
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow(() -> new UnauthorizedException("please login first."));
        req.ids = request.ids;
        req.operator = "book-site-frontend";
        notificationWebService.deleteBatch(req);
    }
}
