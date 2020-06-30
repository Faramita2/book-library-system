package app.booksitefrontend;

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

        load(new UserModule());
    }
}
