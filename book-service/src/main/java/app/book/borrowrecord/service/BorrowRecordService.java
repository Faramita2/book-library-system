package app.book.borrowrecord.service;

import app.book.api.author.AuthorView;
import app.book.api.borrowrecord.ReturnBookRequest;
import app.book.api.borrowrecord.SearchBorrowRecordRequest;
import app.book.api.borrowrecord.SearchBorrowRecordResponse;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.book.book.domain.Book;
import app.book.book.domain.BookStatus;
import app.book.borrowrecord.domain.BorrowRecord;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.log.Markers;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author zoo
 */
public class BorrowRecordService {
    private final Logger logger = LoggerFactory.getLogger(BorrowRecordService.class);
    @Inject
    MongoCollection<BorrowRecord> borrowRecordMongoCollection;
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Redis redis;

    public void returnBook(String id, ReturnBookRequest request) {
        BorrowRecord borrowRecord = borrowRecordMongoCollection.get(new ObjectId(id)).orElseThrow(() -> new NotFoundException(
            Strings.format("borrow record not found, id = {}", id), "BORROW_RECORD_NOT_FOUND"));
        Book book = bookRepository.get(borrowRecord.book.id).orElseThrow(() -> new NotFoundException(
            Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));

        if (borrowRecord.actualReturnDate != null) {
            throw new BadRequestException("book has been returned!", "BOOK_RETURNED");
        }

        String returnBookLock = Strings.format("return.book.{}", id);
        if (!redis.set(returnBookLock, "1", Duration.ofSeconds(2), true)) {
            throw new BadRequestException("server is busy now.", "RETURN_BOOK_ERROR");
        }

        LocalDateTime now = LocalDateTime.now();
        borrowRecord.actualReturnDate = now;
        borrowRecord.updatedBy = request.requestedBy;
        borrowRecord.updatedTime = now;

        book.status = BookStatus.AVAILABLE;
        book.returnDate = null;
        book.borrowedTime = null;
        book.borrowUserId = null;
        book.updatedBy = request.requestedBy;
        book.updatedTime = now;

        try {
            borrowRecordMongoCollection.replace(borrowRecord);
            bookRepository.update(book);
        } catch (Exception e) {
            logger.error(Markers.errorCode("RETURN_BOOK_ERROR"), e.getMessage());
        } finally {
            redis.del(returnBookLock);
        }
    }

    public SearchBorrowRecordResponse search(SearchBorrowRecordRequest request) {
        Query query = new Query();
        query.filter = eq("user.id", request.borrowUserId);
        query.skip = request.skip;
        query.limit = request.limit;

        SearchBorrowRecordResponse response = new SearchBorrowRecordResponse();
        response.total = borrowRecordMongoCollection.count(query.filter);
        response.records = borrowRecordMongoCollection.find(query).stream().map(borrowRecord -> {
            SearchBorrowRecordResponse.Book bookView = new SearchBorrowRecordResponse.Book();
            bookView.id = borrowRecord.book.id;
            bookView.name = borrowRecord.book.name;
            bookView.description = borrowRecord.book.description;
            bookView.authors = borrowRecord.book.authors.stream().map(this::author).collect(Collectors.toList());
            bookView.categories = borrowRecord.book.categories.stream().map(this::category).collect(Collectors.toList());
            bookView.tags = borrowRecord.book.tags.stream().map(this::tag).collect(Collectors.toList());

            SearchBorrowRecordResponse.Record view = new SearchBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.book = bookView;
            view.borrowedTime = borrowRecord.borrowedTime;
            view.returnDate = borrowRecord.returnDate.toLocalDate();
            view.actualReturnDate = borrowRecord.actualReturnDate != null ? borrowRecord.actualReturnDate.toLocalDate() : null;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    private TagView tag(BorrowRecord.Tag tag) {
        TagView tagView = new TagView();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }

    private CategoryView category(BorrowRecord.Category category) {
        CategoryView categoryView = new CategoryView();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private AuthorView author(BorrowRecord.Author author) {
        AuthorView authorView = new AuthorView();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }
}
