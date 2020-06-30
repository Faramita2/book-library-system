package app.borrowrecord.borrowrecord.job;

import app.book.api.BookWebService;
import app.borrowrecord.api.borrowrecord.kafka.ReturnBorrowedBookMessage;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.borrowrecord.borrowrecord.service.BorrowRecordService;
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
    @Inject
    BookWebService bookWebService;

    @Override
    public void execute(JobContext context) {
        List<BorrowRecord> records = service.findNeedReturnRecords();
        records.forEach(borrowRecord -> {
            ReturnBorrowedBookMessage message = new ReturnBorrowedBookMessage();
            message.bookName = bookWebService.get(borrowRecord.bookId).name;
            message.userId = borrowRecord.borrowerId;
            message.borrowedAt = borrowRecord.borrowedAt;
            message.returnAt = borrowRecord.returnAt;
            message.operator = "BorrowRecordService";

            logger.info("send message, book_name = {}, user_id = {}, borrowed_at = {}, return_at = {}, operator = {}",
                message.bookName, message.userId, message.borrowedAt, message.returnAt, message.operator);
            publisher.publish("return-borrowed-book", message);
        });
    }
}
