package app.borrowrecord.borrowrecord.service;

import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.ListNeedReturnBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import app.borrowrecord.borrowrecord.domain.BorrowRecord;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;

/**
 * @author zoo
 */
public class BorrowRecordService {
    @Inject
    MongoCollection<BorrowRecord> collection;

    public void create(CreateBorrowRecordRequest request) {
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
        borrowRecord.book = book;
        borrowRecord.borrowUserId = request.borrowUserId;
        borrowRecord.borrowedTime = request.borrowedTime;
        LocalDateTime now = LocalDateTime.now();
        borrowRecord.returnDate = request.returnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        borrowRecord.createdTime = now;
        borrowRecord.updatedTime = now;
        borrowRecord.createdBy = request.operator;
        borrowRecord.updatedBy = request.operator;

        collection.insert(borrowRecord);
    }

    public GetBorrowRecordResponse get(String id) {
        BorrowRecord borrowRecord = collection.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("borrow record not found, id = {}", id)));
        GetBorrowRecordResponse response = new GetBorrowRecordResponse();
        response.id = borrowRecord.id.toString();
        response.bookId = borrowRecord.book.id;
        response.borrowUserId = borrowRecord.borrowUserId;
        response.borrowedTime = borrowRecord.borrowedTime;
        response.returnDate = borrowRecord.returnDate.toLocalDate();
        response.actualReturnDate = borrowRecord.actualReturnDate.toLocalDate();

        return response;
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
            view.bookId = borrowRecord.book.id;
            view.borrowUserId = borrowRecord.borrowUserId;
            view.borrowedTime = borrowRecord.borrowedTime;
            view.returnDate = borrowRecord.returnDate.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(String id, UpdateBorrowRecordRequest request) {
        BorrowRecord borrowRecord = collection.get(id).orElseThrow(() ->
            new NotFoundException(Strings.format("borrow record not found, id = {}", id), "BORROW_RECORD_NOT_FOUND"));
        borrowRecord.actualReturnDate = request.actualReturnDate.atStartOfDay().plusDays(1).minusSeconds(1);
        collection.replace(borrowRecord);
    }

    public SearchBorrowRecordResponse search(SearchBorrowRecordRequest request) {
        List<Bson> filters = Lists.newArrayList();
        filters.add(eq("borrow_user_id", request.borrowUserId));
        optionalQuery(request, filters);

        Query query = new Query();
        query.filter = and(filters);
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
            view.actualReturnDate = borrowRecord.actualReturnDate.toLocalDate();
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    private void optionalQuery(SearchBorrowRecordRequest request, List<Bson> filters) {
        if (!Strings.isBlank(request.bookName)) {
            filters.add(Filters.regex("book.name", Pattern.compile(request.bookName)));
        }

        if (!Strings.isBlank(request.bookDescription)) {
            filters.add(Filters.regex("book.description", Pattern.compile(request.bookDescription)));
        }

        if (request.authorIds != null && !request.authorIds.isEmpty()) {
            filters.add(in("book.authors.id", request.authorIds));
        }

        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            filters.add(in("book.categories.id", request.categoryIds));
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            filters.add(in("book.tags.id", request.tagIds));
        }

        if (request.borrowedDate != null) {
            filters.add(and(
                gte("borrowed_time", request.borrowedDate.atStartOfDay()),
                lt("borrowed_time", request.borrowedDate.atStartOfDay().plusDays(1))
            ));
        }

        if (request.returnDate != null) {
            filters.add(eq("return_date", request.returnDate.atStartOfDay().plusDays(1).minusSeconds(1)));
        }

        if (request.actualReturnDate != null) {
            filters.add(eq("actual_return_date", request.actualReturnDate.atStartOfDay().plusDays(1).minusSeconds(1)));
        }
    }
}
