package app.notification;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class NotificationServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        modules();
    }

    private void modules() {
        load(new NotificationModule());
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
    }
}
