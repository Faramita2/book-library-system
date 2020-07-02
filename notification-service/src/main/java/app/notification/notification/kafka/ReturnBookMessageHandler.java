package app.notification.notification.kafka;

import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import app.notification.notification.service.NotificationService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

/**
 * @author zoo
 */
public class ReturnBookMessageHandler implements MessageHandler<ReturnBorrowedBookMessage> {
    private final Logger logger = LoggerFactory.getLogger(ReturnBookMessageHandler.class);
    @Inject
    NotificationService service;

    @Override
    public void handle(String key, ReturnBorrowedBookMessage value) {
        service.create(value);
        String content = Strings.format(
            "The book '{}' you borrowed at {} should be returned tomorrow({}).",
            value.bookName,
            value.borrowedAt.format(DateTimeFormatter.ISO_DATE),
            value.returnAt.format(DateTimeFormatter.ISO_DATE)
        );
        logger.info("send email to user(id = {}): {}", value.userId, content);
    }
}
