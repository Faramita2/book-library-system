package app.notification;

import app.notification.api.NotificationWebService;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.domain.Notification;
import app.notification.notification.kafka.ReturnBookMessageHandler;
import app.notification.notification.service.NotificationService;
import app.notification.notification.web.NotificationWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        // todo do need extract
        db().repository(Notification.class);
        bind(NotificationService.class);
        api().service(NotificationWebService.class, bind(NotificationWebServiceImpl.class));
        kafka().subscribe("return-borrowed-book", ReturnBorrowedBookMessage.class, bind(ReturnBookMessageHandler.class));
    }

}
