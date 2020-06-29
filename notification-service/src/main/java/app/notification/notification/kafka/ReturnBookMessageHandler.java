package app.notification.notification.kafka;

import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.service.NotificationService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;

/**
 * @author zoo
 */
public class ReturnBookMessageHandler implements MessageHandler<ReturnBorrowedBookMessage> {
    @Inject
    NotificationService service;

    @Override
    public void handle(String key, ReturnBorrowedBookMessage value) {
        service.create(value);
    }
}
