package app.book;

import app.borrowrecord.api.BorrowRecordWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BookServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        apiClients();

        modules();
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }

    private void apiClients() {
        api().client(BorrowRecordWebService.class, requiredProperty("app.borrowRecord.ServiceURL"));
    }

    private void modules() {
        load(new BookModule());
        load(new AuthorModule());
        load(new CategoryModule());
        load(new TagModule());
    }
}
