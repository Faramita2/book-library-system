package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
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
    MongoCollection<BorrowRecord> collection;

    public void create(CreateBorrowRecordRequest request) {
        BorrowRecord.User user = new BorrowRecord.User();
        user.id = request.borrowUserId;
        user.username = request.borrowUsername;

        BorrowRecord.Book book = new BorrowRecord.Book();
        book.id = request.bookId;
        book.name = request.bookName;
        book.description = request.bookDescription;
        book.authors = request.authors.stream().map(author -> {
            BorrowRecord.Author view = new BorrowRecord.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());
        book.categories = request.categories.stream().map(category -> {
            BorrowRecord.Category view = new BorrowRecord.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());
        book.tags = request.tags.stream().map(tag -> {
            BorrowRecord.Tag view = new BorrowRecord.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.id = ObjectId.get();
        borrowRecord.user = user;
        borrowRecord.book = book;
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.borrowedTime = now;
        borrowRecord.returnDate = request.returnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        borrowRecord.createdTime = now;
        borrowRecord.updatedTime = now;
        borrowRecord.createdBy = request.requestedBy;
        borrowRecord.updatedBy = request.requestedBy;

        collection.insert(borrowRecord);
    }

    public GetBorrowRecordResponse get(String id) {
        BorrowRecord borrowRecord = collection.get(new ObjectId(id)).orElseThrow(() ->
            new NotFoundException(Strings.format("borrow record not found, id = {}", id)));
        GetBorrowRecordResponse response = new GetBorrowRecordResponse();
        response.id = borrowRecord.id.toString();
        response.bookId = borrowRecord.book.id;
        response.borrowUserId = borrowRecord.user.id;
        response.borrowedTime = borrowRecord.borrowedTime;
        response.returnDate = borrowRecord.returnDate.toLocalDate();
        response.actualReturnDate = borrowRecord.actualReturnDate != null ? borrowRecord.actualReturnDate.toLocalDate() : null;

        return response;
    }

    public void update(String id, UpdateBorrowRecordRequest request) {
        BorrowRecord borrowRecord = collection.get(new ObjectId(id)).orElseThrow(() -> new NotFoundException(Strings.format("borrow record not found, id = {}", id), "BORROW_RECORD_NOT_FOUND"));
        borrowRecord.actualReturnDate = request.actualReturnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        borrowRecord.updatedBy = request.requestedBy;
        borrowRecord.updatedTime = LocalDateTime.now();

        collection.replace(borrowRecord);
    }

    public SearchBorrowRecordResponse search(SearchBorrowRecordRequest request) {
        Query query = new Query();
        query.filter = eq("user.id", request.borrowUserId);
        query.skip = request.skip;
        query.limit = request.limit;

        SearchBorrowRecordResponse response = new SearchBorrowRecordResponse();
        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            SearchBorrowRecordResponse.Book bookView = new SearchBorrowRecordResponse.Book();
            bookView.id = borrowRecord.book.id;
            bookView.name = borrowRecord.book.name;
            bookView.description = borrowRecord.book.description;
            bookView.authors = borrowRecord.book.authors.stream().map(author -> {
                SearchBorrowRecordResponse.Author authorView = new SearchBorrowRecordResponse.Author();
                authorView.id = author.id;
                authorView.name = author.name;
                return authorView;
            }).collect(Collectors.toList());
            bookView.categories = borrowRecord.book.categories.stream().map(category -> {
                SearchBorrowRecordResponse.Category categoryView = new SearchBorrowRecordResponse.Category();
                categoryView.id = category.id;
                categoryView.name = category.name;
                return categoryView;
            }).collect(Collectors.toList());
            bookView.tags = borrowRecord.book.tags.stream().map(tag -> {
                SearchBorrowRecordResponse.Tag tagView = new SearchBorrowRecordResponse.Tag();
                tagView.id = tag.id;
                tagView.name = tag.name;
                return tagView;
            }).collect(Collectors.toList());

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
}
