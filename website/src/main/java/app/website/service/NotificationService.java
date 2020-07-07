package app.website.service;

import app.api.website.notification.DeleteBatchNotificationAJAXRequest;
import app.api.website.notification.GetNotificationAJAXResponse;
import app.api.website.notification.SearchNotificationAJAXRequest;
import app.api.website.notification.SearchNotificationAJAXResponse;
import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.GetNotificationResponse;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

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
        searchNotificationRequest.userId = userId();
        SearchNotificationResponse searchNotificationResponse = notificationWebService.search(searchNotificationRequest);

        SearchNotificationAJAXResponse response = new SearchNotificationAJAXResponse();
        response.total = searchNotificationResponse.total;
        response.notifications = searchNotificationResponse.notifications.stream().map(notification -> {
            SearchNotificationAJAXResponse.Notification view = new SearchNotificationAJAXResponse.Notification();
            view.id = notification.id;
            view.content = notification.content;
            view.createdTime = notification.createdTime;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetNotificationAJAXResponse get(Long id) {
        GetNotificationResponse getNotificationResponse = notificationWebService.get(id);
        GetNotificationAJAXResponse response = new GetNotificationAJAXResponse();
        response.id = getNotificationResponse.id;
        response.content = getNotificationResponse.content;
        response.createdTime = getNotificationResponse.createdTime;

        return response;
    }

    public void delete(Long id) {
        DeleteNotificationRequest deleteNotificationRequest = new DeleteNotificationRequest();
        deleteNotificationRequest.userId = userId();
        deleteNotificationRequest.requestedBy = username();

        notificationWebService.delete(id, deleteNotificationRequest);
    }

    public void deleteBatch(DeleteBatchNotificationAJAXRequest request) {
        DeleteBatchNotificationRequest deleteBatchNotificationRequest = new DeleteBatchNotificationRequest();
        deleteBatchNotificationRequest.ids = request.ids;
        deleteBatchNotificationRequest.requestedBy = username();

        notificationWebService.deleteBatch(deleteBatchNotificationRequest);
    }

    private Long userId() {
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return Long.valueOf(userId);
    }

    private String username() {
        return webContext.request().session().get("username").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
