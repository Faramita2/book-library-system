package app.book;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.book.domain.AuthorIdView;
import app.book.book.domain.Book;
import app.book.book.domain.BookCountView;
import app.book.book.domain.BookIdView;
import app.book.book.domain.BookView;
import app.book.book.domain.CategoryIdView;
import app.book.book.domain.TagIdView;
import app.book.book.service.BOBookService;
import app.book.book.service.BookService;
import app.book.book.web.BOBookWebServiceImpl;
import app.book.book.web.BookWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BookModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));
        api().service(BookWebService.class, bind(BookWebServiceImpl.class));
    }

    private void services() {
        bind(BOBookService.class);
        bind(BookService.class);
    }

    private void dbs() {
        db().repository(Book.class);
        db().view(BookView.class);
        db().view(BookCountView.class);
        db().view(TagIdView.class);
        db().view(CategoryIdView.class);
        db().view(AuthorIdView.class);
        db().view(BookIdView.class);
    }
}
