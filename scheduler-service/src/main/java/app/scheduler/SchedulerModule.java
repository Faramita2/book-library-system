package app.scheduler;

import app.borrowrecord.api.borrowrecord.kafka.ReturnBorrowedBookMessage;
import app.scheduler.job.FindNeedReturnBorrowRecordJob;
import core.framework.module.Module;

import java.time.LocalTime;

/**
 * @author meow
 */
public class SchedulerModule extends Module {
    @Override
    protected void initialize() {
        kafka().publish("return-borrowed-book", ReturnBorrowedBookMessage.class);
        schedule().dailyAt("find-need-return-record", bind(FindNeedReturnBorrowRecordJob.class), LocalTime.of(23, 59));
    }
}
