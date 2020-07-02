package app.admin;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class AdminServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        modules();
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
    }

    private void modules() {
        load(new AdminModule());
    }
}
