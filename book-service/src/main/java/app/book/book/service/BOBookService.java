package app.book.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BookStatusView;
import app.book.book.domain.Book;
import app.book.book.domain.BookStatus;
import app.book.book.domain.SearchBook;
import core.framework.db.Database;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOBookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Database database;

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

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();
        StringBuilder sql = getSearchSql();

        ArrayList<Object> args = new ArrayList<>();

        if (request.name != null) {
            sql.append(" AND `b`.`name` like ?%");
            args.add(request.name);
        }

        if (request.tagId != null) {
            sql.append(" AND `t`.`id` = ?");
            args.add(request.tagId);
        }

        if (request.description != null) {
            sql.append(" AND `b`.`description` like ?%");
            args.add(request.description);
        }

        if (request.categoryId != null) {
            sql.append(" AND `c`.`id` = ?");
            args.add(request.categoryId);
        }

        if (request.authorId != null) {
            sql.append(" AND `a`.`id` = ?");
            args.add(request.authorId);
        }

        response.books = database.select(sql.toString(), SearchBook.class, args.toArray()).stream().map(searchBook -> {
            BOSearchBookResponse.Book book = new BOSearchBookResponse.Book();

            book.id = searchBook.id;
            book.name = searchBook.name;
            book.description = searchBook.description;
            book.authorName = searchBook.authorName == null ? "-" : searchBook.authorName;
            book.categoryName = searchBook.categoryName == null ? "-" : searchBook.categoryName;
            book.tagName = searchBook.tagName == null ? "-" : searchBook.tagName;
            book.status = BookStatusView.valueOf(searchBook.status.name());
            book.borrowerName = searchBook.borrowerName;
            book.borrowedAt = searchBook.borrowedAt;
            book.returnAt = searchBook.returnAt;

            return book;
        }).collect(Collectors.toList());
        response.total = (long) response.books.size();

        return response;
    }

    private StringBuilder getSearchSql() {
        StringBuilder sql = new StringBuilder(690);
        sql.append("SELECT"
            + " `b`.`id` `id`, `b`.`name` `name`, `b`.`description` `description`, `b`.`status` `status`,"
            + " `b`.`borrowed_at` `borrowed_at`, `b`.`return_at` `return_at`,"
            + " `a`.`name` `author_name`, `c`.`name` `category_name`, `t`.`name` `tag_name`, `u`.`username` `borrower_name`"
            + " FROM `books` `b`" + " LEFT JOIN `book_authors` `ba` ON `b`.`id` = `ba`.`book_id`"
            + " LEFT JOIN `book_tags` `bt` ON `b`.`id` = `bt`.`book_id`" + " LEFT JOIN `book_categories` `bc` ON `b`.`id` = `bc`.`book_id`"
            + " LEFT JOIN `authors` `a` ON `a`.`id` = `ba`.`author_id`" + " LEFT JOIN `tags` `t` ON `t`.`id` = `bt`.`tag_id`"
            + " LEFT JOIN `categories` `c` ON `c`.`id` = `bc`.`category_id`" + " LEFT JOIN `users` `u` ON `u`.`id` = `b`.`borrower_id`"
            + " WHERE 1 = 1");

        return sql;
    }
}
