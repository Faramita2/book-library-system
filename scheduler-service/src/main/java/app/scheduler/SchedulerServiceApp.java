package app.scheduler;

import app.book.api.BookWebService;
import app.borrowrecord.api.NeedReturnBorrowRecordWebService;
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
        modules();
    }

    private void apiClients() {
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(NeedReturnBorrowRecordWebService.class, requiredProperty("app.borrowRecord.ServiceURL"));
    }

    private void modules() {
        load(new SchedulerModule());
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
