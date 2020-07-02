package app.borrowrecord;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BorrowRecordServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        modules();
    }

    private void modules() {
        load(new BorrowRecordModule());
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
    }
}
