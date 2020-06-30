package app.booksitefrontend;

import app.api.booksitefrontend.NotificationAJAXWebService;
import app.booksitefrontend.notification.service.NotificationService;
import app.booksitefrontend.notification.web.NotificationAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author meow
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        bind(NotificationService.class);
        api().service(NotificationAJAXWebService.class, bind(NotificationAJAXWebServiceImpl.class));
    }
}
