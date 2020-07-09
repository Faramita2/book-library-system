package app.scheduler;

import app.book.api.kafka.ReturnBorrowedBookMessage;
import app.scheduler.job.NotifyUserReturnBookJob;
import core.framework.module.Module;

import java.time.LocalTime;

/**
 * @author meow
 */
public class SchedulerModule extends Module {
    @Override
    protected void initialize() {
        kafka().publish("return-borrowed-book", ReturnBorrowedBookMessage.class);
        schedule().dailyAt("notify-user-return-book", bind(NotifyUserReturnBookJob.class), LocalTime.of(23, 30));
    }
}
