package app.notification.notification.kafka;

import app.notification.api.notification.kafka.ReturnBorrowedMessage;
import app.notification.notification.service.NotificationService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;

/**
 * @author zoo
 */
public class ReturnBookMessageHandler implements MessageHandler<ReturnBorrowedMessage> {
    @Inject
    NotificationService service;

    @Override
    public void handle(String key, ReturnBorrowedMessage value) {
        service.create(value);
    }
}
