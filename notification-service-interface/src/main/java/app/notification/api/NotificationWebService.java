package app.notification.api;

import app.notification.api.notification.DeleteBatchNotificationRequest;
import app.notification.api.notification.DeleteNotificationRequest;
import app.notification.api.notification.SearchNotificationRequest;
import app.notification.api.notification.SearchNotificationResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author zoo
 */
public interface NotificationWebService {
    @PUT
    @Path("/notification")
    SearchNotificationResponse search(SearchNotificationRequest request);

    @DELETE
    @Path("/notification")
    void deleteBatch(DeleteBatchNotificationRequest request);

    @DELETE
    @Path("/notification/:id")
    void delete(@PathParam("id") Long id, DeleteNotificationRequest request);
}
