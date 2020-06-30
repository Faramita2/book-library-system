package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOBorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;

    public BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request) {
        BOSearchBorrowRecordResponse response = new BOSearchBorrowRecordResponse();
        Query query = new Query();

        query.skip = request.skip;
        query.limit = request.limit;
        query.filter = Filters.eq("book_id", request.bookId);

        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            BOSearchBorrowRecordResponse.Record view = new BOSearchBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.borrowerId = borrowRecord.borrowerId;
            view.borrowedAt = borrowRecord.borrowedAt;
            view.returnAt = borrowRecord.returnAt.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
