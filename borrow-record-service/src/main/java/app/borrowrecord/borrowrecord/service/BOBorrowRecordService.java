package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import org.bson.conversions.Bson;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOBorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;

    public BOSearchBookBorrowRecordResponse search(BOSearchBookBorrowRecordRequest request) {
        BOSearchBookBorrowRecordResponse response = new BOSearchBookBorrowRecordResponse();
        Query query = new Query();

        query.skip = request.skip;
        query.limit = request.limit;

        Bson filter = query.filter;
        if (request.bookId != null) {
            filter = Filters.eq("book_id", request.bookId);
            query.filter = filter;
        }

        response.total = collection.count(filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            BOSearchBookBorrowRecordResponse.Record record = new BOSearchBookBorrowRecordResponse.Record();
            record.id = borrowRecord.id.toString();
            record.bookName = borrowRecord.bookName;
            record.borrowerName = borrowRecord.borrowerName;
            record.borrowerId = borrowRecord.borrowerId;
            record.borrowedAt = borrowRecord.borrowedAt;
            record.returnAt = borrowRecord.returnAt;

            return record;
        }).collect(Collectors.toList());

        return response;
    }
}
