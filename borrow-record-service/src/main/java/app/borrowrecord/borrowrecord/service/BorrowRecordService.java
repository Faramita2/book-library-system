package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.ListNeedReturnBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;

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
        borrowRecord.borrowedAt = request.borrowedAt;
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.returnAt = request.returnAt.atStartOfDay();
        borrowRecord.createdAt = now;
        borrowRecord.updatedAt = now;
        borrowRecord.createdBy = request.operator;
        borrowRecord.updatedBy = request.operator;
        collection.insert(borrowRecord);
    }

    public ListNeedReturnBorrowRecordResponse findNeedReturnRecords() {
        Query query = new Query();
        query.filter = and(
            gte("return_at", LocalDate.now().atStartOfDay().plusDays(1)),
            lt("return_at", LocalDate.now().atStartOfDay().plusDays(2))
        );

        ListNeedReturnBorrowRecordResponse response = new ListNeedReturnBorrowRecordResponse();
        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            ListNeedReturnBorrowRecordResponse.Record view = new ListNeedReturnBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.bookId = borrowRecord.bookId;
            view.borrowerId = borrowRecord.borrowerId;
            view.borrowedAt = borrowRecord.borrowedAt;
            view.returnAt = borrowRecord.returnAt.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
