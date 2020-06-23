package app.book.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BookStatusView;
import app.book.book.domain.Book;
import app.book.book.domain.BookCountView;
import app.book.book.domain.BookStatus;
import app.book.book.domain.BookView;
import core.framework.db.Database;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOBookService {
    private final Logger logger = LoggerFactory.getLogger(BOBookService.class);
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Database database;

    public void create(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.description = request.description;
        book.status = BookStatus.NORMAL;
        book.borrowerId = 0L;
        book.createdAt = LocalDateTime.now();
        book.updatedAt = LocalDateTime.now();
        book.createdBy = "BookService";
        book.updatedBy = "BookService";

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start create book ====");
            Long id = bookRepository.insert(book).orElseThrow();
            insertBookAuthors(id, request.authorIds);
            insertBookTags(id, request.tagIds);
            insertBookCategories(id, request.categoryIds);
            transaction.commit();
            logger.warn("==== end create book ====");
        }
    }

    public BOGetBookResponse get(Long id) {
        StringBuilder sql = getSearchSql();
        sql.append(" AND `b`.`id` = ?");
        BookView view = database.selectOne(sql.toString(), BookView.class, id).orElseThrow(
            () -> new NotFoundException(Strings.format("book not found, id = {}", id))
        );

        BOGetBookResponse response = new BOGetBookResponse();
        response.id = view.id;
        response.name = view.name;
        response.description = view.description;
        response.authorName = view.authorName == null ? "-" : view.authorName;
        response.categoryName = view.categoryName == null ? "-" : view.categoryName;
        response.tagName = view.tagName == null ? "-" : view.tagName;
        response.status = BookStatusView.valueOf(view.status.name());
        response.borrowerName = view.borrowerName == null ? "-" : view.borrowerName;
        response.borrowedAt = view.borrowedAt;
        response.returnAt = view.returnAt;

        return response;
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();

        response.total = getSearchBooksTotal(request);
        response.books = getPagedSearchBooks(request);

        return response;
    }

    private Long getSearchBooksTotal(BOSearchBookRequest request) {
        StringBuilder sql = getCountSearchSql();
        List<Object> args = Lists.newArrayList();

        buildSqlWhere(request, sql, args);

        logger.info("execute sql = {}", sql);
        return database.selectOne(sql.toString(), BookCountView.class, args.toArray()).orElseGet(() -> {
            BookCountView view = new BookCountView();
            view.total = 0L;

            return view;
        }).total;
    }

    private void buildSqlWhere(BOSearchBookRequest request, StringBuilder sql, List<Object> args) {
        if (request.name != null) {
            sql.append(" AND `b`.`name` like ?%");
            args.add(request.name);
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            sql.append(" AND `t`.`id` IN(?)");
            args.add(request.tagIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        if (request.description != null) {
            sql.append(" AND `b`.`description` like ?%");
            args.add(request.description);
        }

        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            sql.append(" AND `c`.`id` IN(?)");
            args.add(request.categoryIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        if (request.authorIds != null && !request.authorIds.isEmpty()) {
            sql.append(" AND `a`.`id` IN(?)");
            args.add(request.authorIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    private List<BOSearchBookResponse.Book> getPagedSearchBooks(BOSearchBookRequest request) {
        StringBuilder sql = getSearchSql();
        List<Object> args = Lists.newArrayList();

        buildSqlWhere(request, sql, args);

        sql.append(" LIMIT ?, ?");
        args.add(request.skip);
        args.add(request.limit);

        return database.select(sql.toString(), BookView.class, args.toArray()).stream().map(searchBook -> {
            BOSearchBookResponse.Book book = new BOSearchBookResponse.Book();

            book.id = searchBook.id;
            book.name = searchBook.name;
            book.description = searchBook.description;
            book.authorName = searchBook.authorName == null ? "-" : searchBook.authorName;
            book.categoryName = searchBook.categoryName == null ? "-" : searchBook.categoryName;
            book.tagName = searchBook.tagName == null ? "-" : searchBook.tagName;
            book.status = BookStatusView.valueOf(searchBook.status.name());
            book.borrowerName = searchBook.borrowerName == null ? "-" : book.borrowerName;
            book.borrowedAt = searchBook.borrowedAt;
            book.returnAt = searchBook.returnAt;

            return book;
        }).collect(Collectors.toList());
    }

    public void update(Long id, BOUpdateBookRequest request) {
        Book book = bookRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("book not found, id = {}", id)));
        book.name = request.name;
        book.description = request.description;

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start update book ====");
            logger.warn("update book name = {}, description = {}", request.name, request.description);
            bookRepository.partialUpdate(book);

            List<Long> authorIds = request.authorIds;
            if (!authorIds.isEmpty()) {
                database.execute("DELETE FROM `book_authors` WHERE `book_id` = ?", id);
                insertBookAuthors(id, authorIds);
            }

            List<Long> tagIds = request.tagIds;
            if (!tagIds.isEmpty()) {
                database.execute("DELETE FROM `book_authors` WHERE `book_id` = ?", id);
                insertBookTags(id, tagIds);
            }

            List<Long> categoryIds = request.categoryIds;
            if (!categoryIds.isEmpty()) {
                database.execute("DELETE FROM `book_authors` WHERE `book_id` = ?", id);
                insertBookCategories(id, categoryIds);
            }

            transaction.commit();
            logger.warn("==== end update book====");
        }
    }

    private void insertBookAuthors(Long id, List<Long> authorIds) {
        StringBuilder sql = new StringBuilder("INSERT INTO `book_authors` (`book_id`, `author_id`) VALUES");

        String[] values = new String[authorIds.size()];
        for (int i = 0; i < authorIds.size(); i++) {
            values[i] = Strings.format("({}, ?)", id);
        }

        sql.append(String.join(",", values));
        logger.info("sql info: {}", sql);
        database.execute(sql.toString(), authorIds.toArray());
    }

    private void insertBookTags(Long id, List<Long> tagIds) {
        StringBuilder sql = new StringBuilder("INSERT INTO `book_tags` (`book_id`, `tag_id`) VALUES");

        String[] values = new String[tagIds.size()];
        for (int i = 0; i < tagIds.size(); i++) {
            values[i] = Strings.format("({}, ?)", id);
        }

        sql.append(String.join(",", values));
        logger.info("sql info: {}", sql);
        database.execute(sql.toString(), tagIds.toArray());
    }

    private void insertBookCategories(Long id, List<Long> categoryIds) {
        StringBuilder sql = new StringBuilder("INSERT INTO `book_categories` (`book_id`, `category_id`) VALUES");

        String[] values = new String[categoryIds.size()];
        for (int i = 0; i < categoryIds.size(); i++) {
            values[i] = Strings.format("({}, ?)", id);
        }

        sql.append(String.join(",", values));
        logger.info("sql info: {}", sql);
        database.execute(sql.toString(), categoryIds.toArray());
    }

    private StringBuilder getCountSearchSql() {
        StringBuilder sql = new StringBuilder(600);
        sql.append("SELECT"
            + " COUNT(`b`.`id`) `total`"
            + " FROM `books` `b`" + " LEFT JOIN `book_authors` `ba` ON `b`.`id` = `ba`.`book_id`"
            + " LEFT JOIN `book_tags` `bt` ON `b`.`id` = `bt`.`book_id`" + " LEFT JOIN `book_categories` `bc` ON `b`.`id` = `bc`.`book_id`"
            + " LEFT JOIN `authors` `a` ON `a`.`id` = `ba`.`author_id`" + " LEFT JOIN `tags` `t` ON `t`.`id` = `bt`.`tag_id`"
            + " LEFT JOIN `categories` `c` ON `c`.`id` = `bc`.`category_id`" + " LEFT JOIN `users` `u` ON `u`.`id` = `b`.`borrower_id`"
            + " WHERE 1 = 1");

        return sql;
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
