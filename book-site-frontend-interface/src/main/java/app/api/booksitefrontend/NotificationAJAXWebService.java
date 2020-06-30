package app.api.booksitefrontend;

import app.api.booksitefrontend.notification.DeleteBatchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXRequest;
import app.api.booksitefrontend.notification.SearchNotificationAJAXResponse;
import core.framework.api.web.service.DELETE;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author meow
 */
public interface NotificationAJAXWebService {
    @PUT
    @Path("/ajax/notification")
    SearchNotificationAJAXResponse search(SearchNotificationAJAXRequest request);

    @DELETE
    @Path("/ajax/notification/:id")
    void delete(@PathParam("id") Long id);

    @DELETE
    @Path("/ajax/notification")
    void deleteBatch(DeleteBatchNotificationAJAXRequest request);
}
