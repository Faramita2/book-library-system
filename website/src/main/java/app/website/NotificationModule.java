package app.website;

import app.api.website.NotificationAJAXWebService;
import app.website.notification.service.NotificationService;
import app.website.web.NotificationAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        services();

        apiServices();
    }

    private void apiServices() {
        api().service(NotificationAJAXWebService.class, bind(NotificationAJAXWebServiceImpl.class));
    }

    private void services() {
        bind(NotificationService.class);
    }
}
