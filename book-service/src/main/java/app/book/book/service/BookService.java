package app.book.book.service;

import app.book.api.book.BookStatusView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.book.domain.AuthorIdView;
import app.book.book.domain.Book;
import app.book.book.domain.BookIdView;
import app.book.book.domain.BookStatus;
import app.book.book.domain.CategoryIdView;
import app.book.book.domain.TagIdView;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Database database;
    @Inject
    BorrowRecordWebService borrowRecordWebService;

    public SearchBookResponse search(SearchBookRequest request) {
        SearchBookResponse response = new SearchBookResponse();
        Query<Book> query = bookRepository.select();
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

        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }

        if (request.borrowerId != null) {
            query.where("borrower_id = ?", request.borrowerId);
        }

        response.total = query.count();
        response.books = query.fetch().stream().map(book -> {
            SearchBookResponse.Book view = new SearchBookResponse.Book();
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

    public GetBookResponse get(Long id) {
        Book book = bookRepository.get(id).orElseThrow(
            () -> new NotFoundException(Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND")
        );

        GetBookResponse response = new GetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.description = book.description;
        response.authorIds = queryAuthorIdsByBookId(id);
        response.categoryIds = queryCategoryIdsByBookId(id);
        response.tagIds = queryTagIdsByBookId(id);
        response.status = BookStatusView.valueOf(book.status.name());
        response.borrowerId = book.borrowerId;
        response.borrowedAt = book.borrowedAt;
        response.returnAt = book.returnAt;

        return response;
    }

    public void borrow(Long id, BorrowBookRequest request) {
        Book book = bookRepository.selectOne("id = ? AND status = ?", id, BookStatus.AVAILABLE.name())
            .orElseThrow(() -> new NotFoundException(Strings.format("book not found or it has been borrowed, id = {}", id), "BOOK_NOT_FOUND"));

        LocalDateTime now = LocalDateTime.now();

        book.status = BookStatus.BORROWED;
        book.borrowerId = request.userId;
        book.borrowedAt = now;
        book.returnAt = request.returnAt;
        book.updatedTime = now;
        book.updatedBy = request.operator;

        try (Transaction transaction = database.beginTransaction()) {
            // todo log
            logger.warn("==== start borrow book ====");
            bookRepository.partialUpdate(book);
            createBorrowRecord(book);
            transaction.commit();
            logger.warn("==== end borrow book ====");
        }
    }

    public void returnBook(Long id, ReturnBookRequest request) {
        // todo divide
        // borrow_user_id
        Book book = bookRepository.selectOne(
            "id = ? AND borrower_id = ? AND status = ? ", id, request.userId, BookStatus.BORROWED.name())
            .orElseThrow(() ->
                new NotFoundException(Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));

        book.status = BookStatus.AVAILABLE;
        book.borrowerId = null;
        book.returnAt = null;
        book.borrowedAt = null;
        book.updatedTime = LocalDateTime.now();
        book.updatedBy = request.operator;

        // todo db transaction!!
        try (Transaction transaction = database.beginTransaction()) {
            logger.warn("==== start return book ====");
            bookRepository.update(book);
            updateBorrowRecord(book);
            transaction.commit();
            logger.warn("==== end borrow book ====");
        }
    }

    private void updateBorrowRecord(Book book) {
        // todo combine
        SearchBorrowRecordRequest searchBorrowRecordRequest = new SearchBorrowRecordRequest();
        searchBorrowRecordRequest.bookId = book.id;
        searchBorrowRecordRequest.borrowerId = book.borrowerId;
        searchBorrowRecordRequest.actualReturnAt = null;
        searchBorrowRecordRequest.skip = 0;
        searchBorrowRecordRequest.limit = 1;
        SearchBorrowRecordResponse.Record record = borrowRecordWebService.search(searchBorrowRecordRequest).records.get(0);

        UpdateBorrowRecordRequest updateBorrowRecordRequest = new UpdateBorrowRecordRequest();
        updateBorrowRecordRequest.actualReturnAt = LocalDate.now();

        borrowRecordWebService.update(record.id, updateBorrowRecordRequest);
    }

    private void createBorrowRecord(Book book) {
        CreateBorrowRecordRequest request = new CreateBorrowRecordRequest();
        request.bookId = book.id;
        request.borrowerId = book.borrowerId;
        request.borrowedAt = book.borrowedAt;
        request.returnAt = book.returnAt;
        request.operator = book.updatedBy;

        borrowRecordWebService.create(request);
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

    private List<Long> queryBookIdsByTagIds(SearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_tags WHERE tag_id IN(?)", BookIdView.class, request.tagIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByCategoryIds(SearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_categories WHERE category_id IN(?)", BookIdView.class, request.categoryIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByAuthorIds(SearchBookRequest request) {
        return database.select(
            "SELECT book_id FROM book_authors WHERE author_id IN(?)", BookIdView.class, request.authorIds)
            .stream()
            .map(bookIdView -> bookIdView.bookId)
            .collect(Collectors.toList());
    }

}
