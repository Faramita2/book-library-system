package app.booksite;

import app.api.admin.BOAdminWebService;
import app.book.api.BOAuthorWebService;
import app.book.api.BOBookWebService;
import app.book.api.BOCategoryWebService;
import app.book.api.BOTagWebService;
import app.borrowrecord.api.BOBorrowRecordWebService;
import app.user.api.BOUserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BookSiteApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        api().client(BOAdminWebService.class, requiredProperty("app.admin.ServiceURL"));
        api().client(BOAuthorWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOCategoryWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOTagWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOBookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOUserWebService.class, requiredProperty("app.user.ServiceURL"));
        api().client(BOBorrowRecordWebService.class, requiredProperty("app.borrowRecord.ServiceURL"));

        load(new AdminModule());
        load(new BookModule());
        load(new BookAuthorModule());
        load(new BookCategoryModule());
        load(new BookTagModule());
        load(new BorrowRecordModule());
        load(new UserModule());
    }
}
