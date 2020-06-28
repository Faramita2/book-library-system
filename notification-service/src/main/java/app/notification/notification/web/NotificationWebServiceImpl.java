package app.notification.notification.web;

import app.notification.api.NotificationWebService;
import app.notification.api.notification.DeleteNotificationRequest;
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
    public void delete(Long id) {
        service.delete(id);
    }

    @Override
    public void deleteBatch(DeleteNotificationRequest request) {
        service.deleteBatch(request);
    }
}
