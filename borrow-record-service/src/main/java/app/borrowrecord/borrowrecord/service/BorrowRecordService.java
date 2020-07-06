package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.ListNeedReturnBorrowRecordResponse;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;

/**
 * @author zoo
 */
public class BorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;

    public void create(CreateBorrowRecordRequest request) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.id = ObjectId.get();
        borrowRecord.bookId = request.bookId;
        borrowRecord.borrowerId = request.borrowerId;
        borrowRecord.borrowedTime = request.borrowedTime;
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.returnDate = request.returnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        borrowRecord.createdTime = now;
        borrowRecord.updatedTime = now;
        borrowRecord.createdBy = request.operator;
        borrowRecord.updatedBy = request.operator;
        collection.insert(borrowRecord);
    }

    public ListNeedReturnBorrowRecordResponse findNeedReturnRecords() {
        Query query = new Query();
        LocalDate.of(2020, 7, 5).atStartOfDay();
        LocalDate.of(2020, 7, 5).atStartOfDay().plusDays(1);
        // todo combine
        query.filter = or(
            and(
                gte("return_date", LocalDate.now().atStartOfDay().plusDays(1)),
                lt("return_date", LocalDate.now().atStartOfDay().plusDays(2)),
                eq("actual_return_date", null)
            ),
            lt("return_date", LocalDate.now().atStartOfDay())
        );

        ListNeedReturnBorrowRecordResponse response = new ListNeedReturnBorrowRecordResponse();
        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            ListNeedReturnBorrowRecordResponse.Record view = new ListNeedReturnBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.bookId = borrowRecord.bookId;
            view.borrowerId = borrowRecord.borrowerId;
            view.borrowedTime = borrowRecord.borrowedTime;
            view.returnDate = borrowRecord.returnDate.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(String id, UpdateBorrowRecordRequest request) {
        BorrowRecord borrowRecord = collection.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("borrow record not found, id = {}", id), "BORROW_RECORD_NOT_FOUND"));
        if (request.actualReturnDate != null) {
            borrowRecord.actualReturnDate = request.actualReturnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        }
        collection.replace(borrowRecord);
    }

    public SearchBorrowRecordResponse search(SearchBorrowRecordRequest request) {
        Query query = new Query();
        query.filter = and(
            eq("borrower_id", request.borrowerId),
            eq("book_id", request.bookId),
            eq("actual_return_date", request.actualReturnDate)
        );
        query.skip = request.skip;
        query.limit = request.limit;

        SearchBorrowRecordResponse response = new SearchBorrowRecordResponse();
        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            SearchBorrowRecordResponse.Record view = new SearchBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.borrowedTime = borrowRecord.borrowedTime;
            view.returnDate = borrowRecord.returnDate.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
