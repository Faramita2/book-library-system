package app.booksitefrontend;

import app.book.api.AuthorWebService;
import app.book.api.BookWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.user.api.UserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author meow
 */
public class BookSiteFrontendApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");

        api().client(UserWebService.class, requiredProperty("app.user.ServiceURL"));
        api().client(AuthorWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(CategoryWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(TagWebService.class, requiredProperty("app.book.ServiceURL"));
        api().client(BookWebService.class, requiredProperty("app.book.ServiceURL"));

        load(new UserModule());
        load(new BookAuthorModule());
        load(new BookCategoryModule());
        load(new BookTagModule());
        load(new BookModule());
    }
}
