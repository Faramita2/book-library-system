package app.booksitefrontend.notification.web;

import app.api.booksitefrontend.NotificationAJAXWebService;
import app.api.booksitefrontend.notification.DeleteBatchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXResponse;
import app.booksitefrontend.notification.service.NotificationService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author meow
 */
public class NotificationAJAXWebServiceImpl implements NotificationAJAXWebService {
    @Inject
    NotificationService service;

    @Override
    public SearchNotificationAJAXResponse search(SearchNotificationAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("notification_id", id);
        service.delete(id);
    }

    @Override
    public void deleteBatch(DeleteBatchNotificationAJAXRequest request) {
        service.deleteBatch(request);
    }
}
