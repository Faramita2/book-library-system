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
        dbs();

        services();

        apiServices();

        async();
    }

    private void async() {
        kafka().subscribe("return-borrowed-book", ReturnBorrowedBookMessage.class, bind(ReturnBookMessageHandler.class));
    }

    private void apiServices() {
        api().service(NotificationWebService.class, bind(NotificationWebServiceImpl.class));
    }

    private void services() {
        bind(NotificationService.class);
    }

    private void dbs() {
        db().repository(Notification.class);
    }
}
