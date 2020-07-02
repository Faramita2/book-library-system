package app.notification.notification.web;

import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.GetNotificationResponse;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import app.notification.notification.service.NotificationService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class NotificationWebServiceImpl implements NotificationWebService {
    @Inject
    NotificationService service;

    @Override
    public SearchNotificationResponse search(SearchNotificationRequest request) {
        return service.search(request);
    }

    @Override
    public GetNotificationResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void delete(Long id, DeleteNotificationRequest request) {
        service.delete(id, request);
    }

    @Override
    public void deleteBatch(DeleteBatchNotificationRequest request) {
        service.deleteBatch(request);
    }
}
