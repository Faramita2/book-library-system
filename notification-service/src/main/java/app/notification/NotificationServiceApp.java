package app.notification;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class NotificationServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new NotificationModule());
    }
}
