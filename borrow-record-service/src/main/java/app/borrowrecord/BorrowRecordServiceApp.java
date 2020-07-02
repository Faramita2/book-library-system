package app.borrowrecord;

import app.book.api.BookWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BorrowRecordServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        apiClients();

        modules();
    }

    private void modules() {
        load(new BorrowRecordModule());
    }

    private void apiClients() {
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
