package app.book.borrowrecord.service;

import app.book.api.borrowrecord.ReturnBookRequest;
import app.book.api.borrowrecord.SearchBorrowRecordRequest;
import app.book.api.borrowrecord.SearchBorrowRecordResponse;
import app.book.book.domain.Book;
import app.book.book.domain.BookStatus;
import app.book.borrowrecord.domain.BorrowRecord;
import core.framework.db.Database;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.log.Markers;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author zoo
 */
public class BorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> borrowRecordMongoCollection;
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Database database;

    public void returnBook(String id, ReturnBookRequest request) {
        BorrowRecord borrowRecord = borrowRecordMongoCollection.get(new ObjectId(id)).orElseThrow(() -> new NotFoundException(
            Strings.format("borrow record not found, id = {}", id), Markers.errorCode("BORROW_RECORD_NOT_FOUND").getName()));

        if (borrowRecord.actualReturnDate != null) {
            // todo pure
            throw new BadRequestException("book has been returned!", Markers.errorCode("BOOK_RETURNED").getName());
        }
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.actualReturnDate = now;
        borrowRecord.updatedBy = request.requestedBy;
        borrowRecord.updatedTime = now;

        Book book = bookRepository.get(borrowRecord.book.id).orElseThrow(() -> new NotFoundException(
            Strings.format("book not found, id = {}", id), Markers.errorCode("BOOK_NOT_FOUND").getName()));
        book.status = BookStatus.AVAILABLE;
        book.returnDate = null;
        book.borrowedTime = null;
        book.borrowUserId = null;
        book.updatedBy = request.requestedBy;
        book.updatedTime = now;

//        try (Transaction transaction = database.beginTransaction()) {
        borrowRecordMongoCollection.replace(borrowRecord);
        bookRepository.update(book);
            // todo

//            transaction.commit();
//        } catch ()
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

    private SearchBorrowRecordResponse.Tag tag(BorrowRecord.Tag tag) {
        SearchBorrowRecordResponse.Tag tagView = new SearchBorrowRecordResponse.Tag();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }

    private SearchBorrowRecordResponse.Category category(BorrowRecord.Category category) {
        SearchBorrowRecordResponse.Category categoryView = new SearchBorrowRecordResponse.Category();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private SearchBorrowRecordResponse.Author author(BorrowRecord.Author author) {
        SearchBorrowRecordResponse.Author authorView = new SearchBorrowRecordResponse.Author();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }
}
