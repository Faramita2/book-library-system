package app.backoffice;

import app.api.admin.BOAdminWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        api().client(BOAdminWebService.class, requiredProperty("app.admin.ServiceURL"));


        load(new AdminModule());
        load(new BookModule());
        load(new BookAuthorModule());
        load(new BookCategoryModule());
        load(new BookTagModule());
        load(new BorrowRecordModule());
        load(new UserModule());
    }
}
