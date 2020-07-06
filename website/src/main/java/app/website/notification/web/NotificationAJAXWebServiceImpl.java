package app.website.notification.web;

import app.api.website.NotificationAJAXWebService;
import app.api.website.notification.DeleteBatchNotificationAJAXRequest;
import app.api.website.notification.GetNotificationAJAXResponse;
import app.api.website.notification.SearchNotificationAJAXRequest;
import app.api.website.notification.SearchNotificationAJAXResponse;
import app.website.notification.service.NotificationService;
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
    public GetNotificationAJAXResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public void delete(Long id) {
        ActionLogContext.put("id", id);
        service.delete(id);
    }

    @Override
    public void deleteBatch(DeleteBatchNotificationAJAXRequest request) {
        ActionLogContext.put("ids", request.ids);
        service.deleteBatch(request);
    }
}
