package app.scheduler;

import app.book.api.BOBorrowRecordWebService;
import app.book.api.BookWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author meow
 */
public class SchedulerServiceApp extends App {
    @Override
    protected void initialize() {
        sys();
        apiClients();
        load(new SchedulerModule());
    }

    private void apiClients() {
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOBorrowRecordWebService.class, requiredProperty("app.book.ServiceURL"));
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
