package app.backoffice;

import app.api.admin.BOAdminWebService;
import app.api.authentication.BOAuthenticationWebService;
import app.backoffice.web.interceptor.AuthInterceptor;
import app.book.api.BOAuthorWebService;
import app.book.api.BOBookWebService;
import app.book.api.BOBorrowRecordWebService;
import app.book.api.BOCategoryWebService;
import app.book.api.BOTagWebService;
import app.user.api.BOUserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        sys();

        apiClients();

        modules();
    }

    private void modules() {
        load(new AdminModule());
        load(new BookModule());
        load(new BookAuthorModule());
        load(new BookCategoryModule());
        load(new BookTagModule());
        load(new BorrowRecordModule());
        load(new UserModule());
    }

    private void apiClients() {
        api().client(BOAdminWebService.class, requiredProperty("app.admin.ServiceURL"));
        api().client(BOAuthenticationWebService.class, requiredProperty("app.authentication.ServiceURL"));
        api().client(BOAuthorWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOBookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOBorrowRecordWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOCategoryWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOTagWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BOUserWebService.class, requiredProperty("app.user.ServiceURL"));

        http().intercept(bind(AuthInterceptor.class));
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
