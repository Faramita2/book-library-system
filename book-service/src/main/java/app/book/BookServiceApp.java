package app.book;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BookServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new BookModule());
    }
}
