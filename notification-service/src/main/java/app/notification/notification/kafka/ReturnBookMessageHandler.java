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
    public void handle(String key, ReturnBorrowedBookMessage message) {
        service.create(message);
        String content = Strings.format(
            "The book [{}] you borrowed at {} should be returned at {}.",
            message.bookName,
            message.borrowedTime.format(DateTimeFormatter.ISO_DATE_TIME),
            message.returnDate.format(DateTimeFormatter.ISO_DATE)
        );
        logger.info("[mock] send email to user(id = {}): {}", message.userId, content);
    }
}
