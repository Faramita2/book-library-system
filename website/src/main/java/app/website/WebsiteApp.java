package app.website;

import app.api.authentication.AuthenticationWebService;
import app.book.api.AuthorWebService;
import app.book.api.BookWebService;
import app.book.api.BorrowRecordWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.notification.api.NotificationWebService;
import app.user.api.UserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author meow
 */
public class WebsiteApp extends App {
    @Override
    protected void initialize() {
        sys();
        apiClients();
        modules();
    }

    private void modules() {
        load(new UserModule());
        load(new BookAuthorModule());
        load(new BookCategoryModule());
        load(new BookTagModule());
        load(new BookModule());
        load(new NotificationModule());
        load(new BorrowRecordModule());
    }

    private void apiClients() {
        api().client(AuthorWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(AuthenticationWebService.class, requiredProperty("app.authentication.ServiceURL"));
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BorrowRecordWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(CategoryWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(NotificationWebService.class, requiredProperty("app.notification.ServiceURL"));
        api().client(TagWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(UserWebService.class, requiredProperty("app.user.ServiceURL"));
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
