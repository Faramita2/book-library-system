package app.book.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BookStatusView;
import app.book.book.domain.AuthorIdView;
import app.book.book.domain.Book;
import app.book.book.domain.BookIdView;
import app.book.book.domain.BookStatus;
import app.book.book.domain.CategoryIdView;
import app.book.book.domain.TagIdView;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
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
    Repository<Book> repository;
    @Inject
    Database database;

    public void create(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.description = request.description;
        book.status = BookStatus.NORMAL;
        book.borrowerId = 0L;
        LocalDateTime now = LocalDateTime.now();
        book.createdAt = now;
        book.updatedAt = now;
        book.createdBy = request.operator;
        book.updatedBy = request.operator;

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start create book ====");
            Long id = repository.insert(book).orElseThrow();
            insertBookAuthors(id, request.authorIds);
            insertBookTags(id, request.tagIds);
            insertBookCategories(id, request.categoryIds);
            transaction.commit();
            logger.warn("==== end create book ====");
        }
    }

    public BOGetBookResponse get(Long id) {
        Book book = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND")
        );

        BOGetBookResponse response = new BOGetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.description = book.description;
        response.authorIds = queryAuthorIdsByBookId(id);
        response.categoryIds = queryCategoryIdsByBookId(id);
        response.tagIds = queryTagIdsByBookId(id);
        response.status = BookStatusView.valueOf(book.status.name());
        response.borrowerId = book.borrowerId;
        response.borrowedAt = book.borrowedAt;
        response.returnAt = book.returnAt == null ? null : book.returnAt.toLocalDate();

        return response;
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();
        Query<Book> query = repository.select();
        query.skip(request.skip);
        query.limit(request.limit);

        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        if (!Strings.isBlank(request.description)) {
            query.where("description LIKE ?", Strings.format("{}%", request.description));
        }

        if (request.authorIds != null && !request.authorIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByAuthorIds(request);
            query.in("id", bookIds);
        }

        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByCategoryIds(request);
            query.in("id", bookIds);
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByTagIds(request);
            query.in("id", bookIds);
        }

        response.total = query.count();
        response.books = query.fetch().stream().map(book -> {
            BOSearchBookResponse.Book view = new BOSearchBookResponse.Book();
            view.id = book.id;
            view.name = book.name;
            view.tagIds = queryTagIdsByBookId(book.id);
            view.description = book.description;
            view.categoryIds = queryCategoryIdsByBookId(book.id);
            view.authorIds = queryAuthorIdsByBookId(book.id);
            view.status = BookStatusView.valueOf(book.status.name());
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateBookRequest request) {
        Book book = repository.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));
        book.name = request.name;
        book.description = request.description;
        book.updatedAt = LocalDateTime.now();
        book.updatedBy = request.operator;

        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start update book ====");
            logger.warn("update book name = {}, description = {}", request.name, request.description);
            repository.partialUpdate(book);

            List<Long> authorIds = request.authorIds;
            if (authorIds != null && !authorIds.isEmpty()) {
                database.execute("DELETE FROM `book_authors` WHERE `book_id` = ?", id);
                insertBookAuthors(id, authorIds);
            }

            List<Long> tagIds = request.tagIds;
            if (tagIds != null && !tagIds.isEmpty()) {
                database.execute("DELETE FROM `book_tags` WHERE `book_id` = ?", id);
                insertBookTags(id, tagIds);
            }

            List<Long> categoryIds = request.categoryIds;
            if (categoryIds != null && !categoryIds.isEmpty()) {
                database.execute("DELETE FROM `book_categories` WHERE `book_id` = ?", id);
                insertBookCategories(id, categoryIds);
            }

            transaction.commit();
            logger.warn("==== end update book====");
        }
    }

    private List<Long> queryTagIdsByBookId(Long bookId) {
        return database.select(
            "SELECT tag_id FROM book_tags WHERE book_id = ?", TagIdView.class, bookId)
            .stream()
            .map(tagIdView -> tagIdView.tagId)
            .collect(Collectors.toList());
    }

    private List<Long> queryCategoryIdsByBookId(Long bookId) {
        return database.select(
            "SELECT category_id FROM book_categories WHERE book_id = ?", CategoryIdView.class, bookId)
            .stream()
            .map(categoryIdView -> categoryIdView.categoryId)
            .collect(Collectors.toList());
    }

    private List<Long> queryAuthorIdsByBookId(Long bookId) {
        return database.select(
            "SELECT author_id FROM book_authors WHERE book_id = ?", AuthorIdView.class, bookId)
            .stream()
            .map(authorIdView -> authorIdView.authorId)
            .collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByTagIds(BOSearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_tags WHERE tag_id IN(?)", BookIdView.class, request.tagIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByCategoryIds(BOSearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_categories WHERE category_id IN(?)", BookIdView.class, request.categoryIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByAuthorIds(BOSearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_authors WHERE author_id IN(?)", BookIdView.class, request.authorIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
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
}
