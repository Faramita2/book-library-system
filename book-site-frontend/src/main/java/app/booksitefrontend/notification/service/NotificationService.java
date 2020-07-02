package app.booksitefrontend.notification.service;

import app.api.booksitefrontend.notification.DeleteBatchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.GetNotificationAJAXResponse;
import app.api.booksitefrontend.notification.SearchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXResponse;
import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.GetNotificationResponse;
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
        SearchNotificationRequest searchNotificationRequest = new SearchNotificationRequest();
        searchNotificationRequest.skip = request.skip;
        searchNotificationRequest.limit = request.limit;
        searchNotificationRequest.content = request.content;
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow(() -> new UnauthorizedException("please login first."));
        searchNotificationRequest.userId = Long.valueOf(userId.get());
        SearchNotificationResponse searchNotificationResponse = notificationWebService.search(searchNotificationRequest);

        SearchNotificationAJAXResponse response = new SearchNotificationAJAXResponse();
        response.total = searchNotificationResponse.total;
        response.notifications = searchNotificationResponse.notifications.stream().map(notification -> {
            SearchNotificationAJAXResponse.Notification view = new SearchNotificationAJAXResponse.Notification();
            view.id = notification.id;
            view.content = notification.content;
            view.createdAt = notification.createdAt;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetNotificationAJAXResponse get(Long id) {
        GetNotificationResponse getNotificationResponse = notificationWebService.get(id);
        GetNotificationAJAXResponse response = new GetNotificationAJAXResponse();
        response.id = getNotificationResponse.id;
        response.content = getNotificationResponse.content;
        response.createdAt = getNotificationResponse.createdAt;

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
