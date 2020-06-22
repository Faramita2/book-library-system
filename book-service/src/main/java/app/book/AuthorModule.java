package app.book;

import app.book.api.AuthorWebService;
import app.book.api.BOAuthorWebService;
import app.book.author.domain.Author;
import app.book.author.service.AuthorService;
import app.book.author.service.BOAuthorService;
import app.book.author.web.AuthorWebServiceImpl;
import app.book.author.web.BOAuthorWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AuthorModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Author.class);
        bind(BOAuthorService.class);
        bind(AuthorService.class);
        api().service(BOAuthorWebService.class, bind(BOAuthorWebServiceImpl.class));
        api().service(AuthorWebService.class, bind(AuthorWebServiceImpl.class));
    }
}
