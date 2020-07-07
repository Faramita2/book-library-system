package app.scheduler.job;

import app.book.api.BookWebService;
import app.borrowrecord.api.SchedulerBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.kafka.ReturnBorrowedBookMessage;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zoo
 */
// todo job target
public class NotifyUserReturnBookJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(NotifyUserReturnBookJob.class);
    @Inject
    SchedulerBorrowRecordWebService schedulerBorrowRecordWebService;
    @Inject
    MessagePublisher<ReturnBorrowedBookMessage> publisher;
    @Inject
    BookWebService bookWebService;

    @Override
    public void execute(JobContext context) {
        schedulerBorrowRecordWebService.list().records.forEach(borrowRecord -> {
                ReturnBorrowedBookMessage message = new ReturnBorrowedBookMessage();
                message.bookName = bookWebService.get(borrowRecord.bookId).name;
                message.userId = borrowRecord.borrowUserId;
                message.borrowedTime = borrowRecord.borrowedTime;
                message.returnDate = borrowRecord.returnDate;
                message.requestedBy = "scheduler-service";

                logger.info("send message, book_name = {}, user_id = {}, borrowed_time = {}, return_date = {}, requested_by = {}",
                    message.bookName, message.userId, message.borrowedTime, message.returnDate, message.requestedBy);
                publisher.publish("return-borrowed-book", message);
            });
    }
}
