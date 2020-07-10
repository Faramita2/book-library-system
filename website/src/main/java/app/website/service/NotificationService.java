package app.website.service;

import app.website.api.notification.DeleteBatchNotificationAJAXRequest;
import app.website.api.notification.GetNotificationAJAXResponse;
import app.website.api.notification.SearchNotificationAJAXRequest;
import app.website.api.notification.SearchNotificationAJAXResponse;
import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.GetNotificationResponse;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class NotificationService {
    @Inject
    NotificationWebService notificationWebService;

    public SearchNotificationAJAXResponse search(SearchNotificationAJAXRequest request, Long userId) {
        SearchNotificationRequest searchNotificationRequest = new SearchNotificationRequest();
        searchNotificationRequest.skip = request.skip;
        searchNotificationRequest.limit = request.limit;
        searchNotificationRequest.content = request.content;
        searchNotificationRequest.userId = userId;
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

    public void delete(Long id, Long userId, String username) {
        DeleteNotificationRequest deleteNotificationRequest = new DeleteNotificationRequest();
        deleteNotificationRequest.userId = userId;
        deleteNotificationRequest.requestedBy = username;

        notificationWebService.delete(id, deleteNotificationRequest);
    }

    public void deleteBatch(DeleteBatchNotificationAJAXRequest request, String username) {
        DeleteBatchNotificationRequest deleteBatchNotificationRequest = new DeleteBatchNotificationRequest();
        deleteBatchNotificationRequest.ids = request.ids;
        deleteBatchNotificationRequest.requestedBy = username;

        notificationWebService.deleteBatch(deleteBatchNotificationRequest);
    }
}
