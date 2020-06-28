package app.notification;

import app.book.api.BookWebService;
import app.notification.api.NotificationWebService;
import app.notification.api.notification.kafka.ReturnBorrowedMessage;
import app.notification.notification.domain.Notification;
import app.notification.notification.kafka.ReturnBookMessageHandler;
import app.notification.notification.service.NotificationService;
import app.notification.notification.web.NotificationWebServiceImpl;
import app.user.api.UserWebService;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Notification.class);
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(UserWebService.class, requiredProperty("app.user.ServiceURL"));
        bind(NotificationService.class);
        kafka().subscribe("return-book", ReturnBorrowedMessage.class, bind(ReturnBookMessageHandler.class));
        api().service(NotificationWebService.class, bind(NotificationWebServiceImpl.class));
    }
}
