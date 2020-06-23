package app.book.borrowedbook.service;

import app.book.api.borrowedbook.SearchBorrowedBookRequest;
import app.book.api.borrowedbook.SearchBorrowedBookResponse;
import app.book.book.domain.BookCountView;
import app.book.borrowedbook.domain.BorrowedBook;
import core.framework.db.Database;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BorrowedBookService {
    private final Logger logger = LoggerFactory.getLogger(BorrowedBookService.class);
    @Inject
    Database database;

    public SearchBorrowedBookResponse search(SearchBorrowedBookRequest request) {
        SearchBorrowedBookResponse response = new SearchBorrowedBookResponse();

        response.total = getSearchBooksTotal(request);
        response.books = getPagedSearchBooks(request);

        return response;
    }

    private Long getSearchBooksTotal(SearchBorrowedBookRequest request) {
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

    private List<SearchBorrowedBookResponse.Book> getPagedSearchBooks(SearchBorrowedBookRequest request) {
        StringBuilder sql = getSearchSql();
        List<Object> args = Lists.newArrayList();

        buildSqlWhere(request, sql, args);

        sql.append(" LIMIT ?, ?");
        args.add(request.skip);
        args.add(request.limit);

        return database.select(sql.toString(), BorrowedBook.class, args.toArray()).stream().map(searchBook -> {
            SearchBorrowedBookResponse.Book book = new SearchBorrowedBookResponse.Book();

            book.id = searchBook.id;
            book.name = searchBook.name;
            book.authorName = searchBook.authorName == null ? "-" : searchBook.authorName;
            book.categoryName = searchBook.categoryName == null ? "-" : searchBook.categoryName;
            book.tagName = searchBook.tagName == null ? "-" : searchBook.tagName;

            return book;
        }).collect(Collectors.toList());
    }

    private void buildSqlWhere(SearchBorrowedBookRequest request, StringBuilder sql, List<Object> args) {
        sql.append(" AND `b`.`borrower_id` = ?");
        args.add(request.userId);

        if (request.name != null) {
            sql.append(" AND `b`.`name` like ?");
            args.add(request.name + "%");
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            sql.append(" AND `t`.`id` IN(?)");
            args.add(request.tagIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        if (request.description != null) {
            sql.append(" AND `b`.`description` like ?");
            args.add(request.description + "%");
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
