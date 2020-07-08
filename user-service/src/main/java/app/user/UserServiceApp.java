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
        modules();
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
    }

    private void modules() {
        load(new UserModule());
    }
}
