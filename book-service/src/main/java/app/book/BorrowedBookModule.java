package app.book;

import app.book.api.BorrowedBookWebService;
import app.book.borrowedbook.domain.BorrowedBook;
import app.book.borrowedbook.service.BorrowedBookService;
import app.book.borrowedbook.web.BorrowedBookWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class BorrowedBookModule extends Module {
    @Override
    protected void initialize() {
        db().view(BorrowedBook.class);
        bind(BorrowedBookService.class);
        api().service(BorrowedBookWebService.class, bind(BorrowedBookWebServiceImpl.class));
    }
}
