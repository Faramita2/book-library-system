package app.book;

import app.book.api.AuthorWebService;
import app.book.api.BOAuthorWebService;
import app.book.author.domain.Author;
import app.book.author.service.AuthorService;
import app.book.author.service.BOAuthorService;
import app.book.author.web.AuthorWebServiceImpl;
import app.book.author.web.BOAuthorWebServiceImpl;
import app.book.book.domain.BookAuthor;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class AuthorModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BOAuthorWebService.class, bind(BOAuthorWebServiceImpl.class));
        api().service(AuthorWebService.class, bind(AuthorWebServiceImpl.class));
    }

    private void services() {
        bind(BOAuthorService.class);
        bind(AuthorService.class);
    }

    private void dbs() {
        db().repository(Author.class);
        db().repository(BookAuthor.class);
    }
}
