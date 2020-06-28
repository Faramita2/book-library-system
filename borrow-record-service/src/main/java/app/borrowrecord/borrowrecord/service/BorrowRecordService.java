package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

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
}
