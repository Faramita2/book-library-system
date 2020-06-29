package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBookBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.user.api.UserWebService;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOBorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;
    @Inject
    UserWebService userWebService;

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
        response.records = getRecords(query);

        return response;
    }

    private List<BOSearchBookBorrowRecordResponse.Record> getRecords(Query query) {
        List<BOSearchBookBorrowRecordResponse.Record> records = collection.find(query).stream().map(borrowRecord -> {
            BOSearchBookBorrowRecordResponse.Record record = new BOSearchBookBorrowRecordResponse.Record();
            record.id = borrowRecord.id.toString();
            record.bookName = borrowRecord.bookName;
            record.borrowerId = borrowRecord.borrowerId;
            record.borrowedAt = borrowRecord.borrowedAt;
            record.returnAt = borrowRecord.returnAt.toLocalDate();

            return record;
        }).collect(Collectors.toList());

        Map<String, String> borrowerNames = getBorrowerNames(query);

        records.forEach(record -> record.borrowerName = borrowerNames.get(record.id));

        return records;
    }

    private Map<String, String> getBorrowerNames(Query query) {
        return collection.find(query).stream().map(borrowRecord -> {
            BOSearchBookBorrowRecordResponse.Record record = new BOSearchBookBorrowRecordResponse.Record();
            record.id = borrowRecord.id.toString();
            record.borrowerName = userWebService.get(borrowRecord.borrowerId).username;

            return record;
        }).collect(Collectors.toMap(record -> record.id, record -> record.borrowerName));
    }
}
