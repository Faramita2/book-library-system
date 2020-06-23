package app.book;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.book.domain.Book;
import app.book.book.domain.BookCountView;
import app.book.book.domain.BookView;
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
        db().repository(Book.class);
        db().view(BookView.class);
        db().view(BookCountView.class);
        bind(BOBookService.class);
        bind(BookService.class);
        api().service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));
        api().service(BookWebService.class, bind(BookWebServiceImpl.class));
    }
}
