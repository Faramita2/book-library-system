package app.user;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class UserServiceApp extends App {
    @Override
    protected void initialize() {
        sys();
        load(new UserModule());
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }
}
