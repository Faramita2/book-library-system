package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import com.mongodb.client.model.Projections;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;

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
        borrowRecord.bookName = request.bookName;
        borrowRecord.borrowerId = request.borrowerId;
        borrowRecord.borrowedAt = request.borrowedAt;
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.returnAt = request.returnAt;
        borrowRecord.createdAt = now;
        borrowRecord.updatedAt = now;
        borrowRecord.createdBy = request.createdBy;
        borrowRecord.updatedBy = request.createdBy;
        collection.insert(borrowRecord);
    }

    public List<BorrowRecord> findNeedReturnRecords() {
        Query query = new Query();
        query.projection = Projections.include("id", "book_name", "borrower_id", "borrowed_at", "return_at");
        query.filter = and(
            gte("return_at", LocalDate.now().atStartOfDay().plusDays(1)),
            lte("return_at", LocalDate.now().atStartOfDay().plusDays(2).minusSeconds(1))
        );

        return collection.find(query);
    }
}
