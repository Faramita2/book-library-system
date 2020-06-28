package app.borrowrecord;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BorrowRecordServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");
        load(new BorrowRecordModule());
    }
}
