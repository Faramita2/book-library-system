package app.borrowrecord.borrowrecord.job;

import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
import app.notification.api.notification.kafka.ReturnBorrowedBookMessage;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zoo
 */
public class FindNeedReturnRecordJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(FindNeedReturnRecordJob.class);
    @Inject
    BorrowRecordService service;
    @Inject
    MessagePublisher<ReturnBorrowedBookMessage> publisher;

    @Override
    public void execute(JobContext context) {
        List<BorrowRecord> records = service.findNeedReturnRecords();
        records.forEach(borrowRecord -> {
            ReturnBorrowedBookMessage message = new ReturnBorrowedBookMessage();
            message.bookId = borrowRecord.bookId;
            message.userId = borrowRecord.borrowerId;
            message.borrowedAt = borrowRecord.borrowedAt;
            message.returnAt = borrowRecord.returnAt;

            logger.info("send message, book_id = {}, user_id = {}, borrowed_at = {}, return_at = {}",
                message.bookId, message.userId, message.borrowedAt, message.returnAt);
            publisher.publish("return-borrowed-book", message);
        });
    }
}
