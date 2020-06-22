package app.book;

import app.book.tag.domain.Tag;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class TagModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Tag.class);
    }
}
