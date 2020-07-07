package app.book;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.book.domain.BookAuthor;
import app.book.book.domain.Book;
import app.book.book.domain.BookCategory;
import app.book.book.domain.BookTag;
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
        db().view(BookTag.class);
        db().view(BookCategory.class);
        db().view(BookAuthor.class);
    }
}
