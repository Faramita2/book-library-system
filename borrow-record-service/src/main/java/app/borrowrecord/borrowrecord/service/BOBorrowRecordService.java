package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import app.user.api.BOUserWebService;
import app.user.api.user.BOSearchUserRequest;
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
    BOUserWebService boUserWebService;

    public BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request) {
        BOSearchBorrowRecordResponse response = new BOSearchBorrowRecordResponse();
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

    private List<BOSearchBorrowRecordResponse.Record> getRecords(Query query) {
        Map<Long, String> borrowerNames = getBorrowerNames(query);

        return collection.find(query).stream().map(borrowRecord -> {
            BOSearchBorrowRecordResponse.Record record = new BOSearchBorrowRecordResponse.Record();
            record.id = borrowRecord.id.toString();
            record.bookName = borrowRecord.bookName;
            record.borrowerId = borrowRecord.borrowerId;
            record.borrowerName = borrowerNames.get(record.borrowerId);
            record.borrowedAt = borrowRecord.borrowedAt;
            record.returnAt = borrowRecord.returnAt.toLocalDate();

            return record;
        }).collect(Collectors.toList());
    }

    private Map<Long, String> getBorrowerNames(Query query) {
        List<Long> borrowerIds = collection.find(query).stream()
            .map(borrowRecord -> borrowRecord.borrowerId)
            .distinct()
            .collect(Collectors.toList());
        BOSearchUserRequest boSearchUserRequest = new BOSearchUserRequest();
        boSearchUserRequest.ids = borrowerIds;

        return boUserWebService.search(boSearchUserRequest).users.stream()
            .collect(Collectors.toMap(user -> user.id, user -> user.username));
    }
}
