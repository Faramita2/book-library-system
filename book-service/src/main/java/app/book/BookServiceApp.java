package app.book;

import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author zoo
 */
public class BookServiceApp extends App {
    @Override
    protected void initialize() {
        sys();

        modules();
    }

    private void sys() {
        load(new SystemModule("sys.properties"));
    }

    private void modules() {
        load(new AuthorModule());
        load(new CategoryModule());
        load(new TagModule());
        load(new BookModule());
    }
}
