package app.book.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.book.domain.Book;
import app.book.book.domain.BookStatus;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class BOBookService {
    @Inject
    Repository<Book> bookRepository;

    public void create(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.tagId = request.tagId;
        book.description = request.description;
        book.categoryId = request.categoryId;
        book.authorId = request.authorId;
        book.status = BookStatus.NORMAL;
        book.borrowerId = 0L;
        book.createdAt = LocalDateTime.now();
        book.updatedAt = LocalDateTime.now();
        book.createdBy = "BookService";
        book.updatedBy = "BookService";

        bookRepository.insert(book).orElseThrow();
    }
}
