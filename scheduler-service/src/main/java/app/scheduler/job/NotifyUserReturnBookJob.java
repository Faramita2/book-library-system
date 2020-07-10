package app.scheduler.job;

import app.book.api.BOBorrowRecordWebService;
import app.book.api.BookWebService;
import app.book.api.kafka.ReturnBorrowedBookMessage;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zoo
 */
public class NotifyUserReturnBookJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(NotifyUserReturnBookJob.class);
    @Inject
    BOBorrowRecordWebService boBorrowRecordWebService;
    @Inject
    MessagePublisher<ReturnBorrowedBookMessage> publisher;
    @Inject
    BookWebService bookWebService;

    @Override
    public void execute(JobContext context) {
        boBorrowRecordWebService.list().records.forEach(borrowRecord -> {
            ReturnBorrowedBookMessage message = new ReturnBorrowedBookMessage();
            message.bookName = bookWebService.get(borrowRecord.bookId).name;
            message.userId = borrowRecord.borrowUserId;
            message.borrowedTime = borrowRecord.borrowedTime;
            message.returnDate = borrowRecord.returnDate;
            message.requestedBy = "scheduler-service";

            logger.info("send message, book_name = {}, user_id = {}, borrowed_time = {}, return_date = {}, requested_by = {}",
                message.bookName, message.userId, message.borrowedTime, message.returnDate, message.requestedBy);
            // todo key
            publisher.publish(message);
        });
    }
}
