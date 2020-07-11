package app.website;

import app.website.api.NotificationAJAXWebService;
import app.website.service.NotificationService;
import app.website.api.NotificationAJAXWebServiceImpl;
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
