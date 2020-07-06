package app.authentication;

import app.api.admin.BOAdminWebService;
import app.user.api.UserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class AuthenticationServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        apiClients();

        modules();
    }

    private void modules() {
        load(new AuthenticationModule());
    }

    private void apiClients() {
        api().client(UserWebService.class, requiredProperty("app.user.ServiceURL"));
        api().client(BOAdminWebService.class, requiredProperty("app.admin.ServiceURL"));
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
