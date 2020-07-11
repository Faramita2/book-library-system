package app.admin;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class AdminServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new AdminModule());
    }
}
