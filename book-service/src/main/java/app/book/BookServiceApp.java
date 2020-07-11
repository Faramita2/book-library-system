package app.book;

import app.book.book.domain.Book;
import app.book.borrowrecord.domain.BorrowRecord;
import core.framework.module.App;
import core.framework.module.SystemModule;
import core.framework.mongo.module.MongoConfig;

/**
 * @author zoo
 */
public class BookServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        dbs();
        modules();
    }

    private void dbs() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowRecord.class);
        db().repository(Book.class);
    }

    private void modules() {
        load(new AuthorModule());
        load(new CategoryModule());
        load(new TagModule());
        load(new BorrowRecordModule());
        load(new BookModule());
    }
}
