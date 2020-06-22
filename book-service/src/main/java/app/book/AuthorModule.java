package app.book;

import app.book.author.domain.Author;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AuthorModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Author.class);
    }
}
