package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Lists;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author zoo
 */
public class BOBorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;

    public BOSearchBorrowRecordResponse search(BOSearchBorrowRecordRequest request) {
        BOSearchBorrowRecordResponse response = new BOSearchBorrowRecordResponse();

        List<Bson> filters = Lists.newArrayList();
        filters.add(eq("book_id", request.bookId));
        if (request.userId != null) {
            filters.add(eq("borrow_user_id", request.userId));
        }

        Query query = new Query();
        query.filter = and(filters);
        query.skip = request.skip;
        query.limit = request.limit;
        response.total = collection.count(query.filter);
        response.records = collection.find(query).stream().map(borrowRecord -> {
            BOSearchBorrowRecordResponse.User userView = new BOSearchBorrowRecordResponse.User();
            userView.id = borrowRecord.user.id;
            userView.username = borrowRecord.user.username;

            BOSearchBorrowRecordResponse.Book bookView = new BOSearchBorrowRecordResponse.Book();
            bookView.id = borrowRecord.book.id;
            bookView.name = borrowRecord.book.name;
            bookView.description = borrowRecord.book.description;
            bookView.authors = borrowRecord.book.authors.stream().map(this::authorView).collect(Collectors.toList());
            bookView.categories = borrowRecord.book.categories.stream().map(this::categoryView).collect(Collectors.toList());
            bookView.tags = borrowRecord.book.tags.stream().map(this::tagView).collect(Collectors.toList());

            BOSearchBorrowRecordResponse.Record view = new BOSearchBorrowRecordResponse.Record();
            view.id = borrowRecord.id.toString();
            view.user = userView;
            view.book = bookView;
            view.borrowedTime = borrowRecord.borrowedTime;
            view.returnDate = borrowRecord.returnDate.toLocalDate();
            view.actualReturnDate = borrowRecord.actualReturnDate.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    private BOSearchBorrowRecordResponse.Author authorView(BorrowRecord.Author author) {
        BOSearchBorrowRecordResponse.Author authorView = new BOSearchBorrowRecordResponse.Author();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }

    private BOSearchBorrowRecordResponse.Category categoryView(BorrowRecord.Category category) {
        BOSearchBorrowRecordResponse.Category categoryView = new BOSearchBorrowRecordResponse.Category();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private BOSearchBorrowRecordResponse.Tag tagView(BorrowRecord.Tag tag) {
        BOSearchBorrowRecordResponse.Tag tagView = new BOSearchBorrowRecordResponse.Tag();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }
}
