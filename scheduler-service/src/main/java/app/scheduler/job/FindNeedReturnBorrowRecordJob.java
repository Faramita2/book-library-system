package app.scheduler.job;

import app.book.api.BookWebService;
import app.borrowrecord.api.NeedReturnBorrowRecordWebService;
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
public class FindNeedReturnBorrowRecordJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(FindNeedReturnBorrowRecordJob.class);
    @Inject
    NeedReturnBorrowRecordWebService needReturnBorrowRecordWebService;
    @Inject
    MessagePublisher<ReturnBorrowedBookMessage> publisher;
    @Inject
    BookWebService bookWebService;

    @Override
    public void execute(JobContext context) {
        needReturnBorrowRecordWebService.list().records.stream()
            // todo
//            .filter(record -> bookWebService.get(record.bookId).status == BookStatusView.NORMAL)
            .forEach(borrowRecord -> {
                ReturnBorrowedBookMessage message = new ReturnBorrowedBookMessage();
                message.bookName = bookWebService.get(borrowRecord.bookId).name;
                message.userId = borrowRecord.borrowerId;
                message.borrowedAt = borrowRecord.borrowedAt;
                message.returnAt = borrowRecord.returnAt;
                message.operator = "scheduler-service";

                logger.info("send message, book_name = {}, user_id = {}, borrowed_at = {}, return_at = {}, operator = {}",
                    message.bookName, message.userId, message.borrowedAt, message.returnAt, message.operator);
                publisher.publish("return-borrowed-book", message);
            });
    }
}
