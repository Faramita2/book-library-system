package app.booksite.borrowrecord.service;

import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXResponse;
import app.book.api.BOBookWebService;
import app.borrowrecord.api.BOBorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.BOSearchBorrowRecordResponse;
import app.user.api.BOUserWebService;
import app.user.api.user.BOSearchUserRequest;
import core.framework.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BOBorrowRecordWebService borrowRecordWebService;
    @Inject
    BOBookWebService bookWebService;
    @Inject
    BOUserWebService userWebService;

    public SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request) {
        BOSearchBorrowRecordRequest boSearchBorrowRecordRequest = new BOSearchBorrowRecordRequest();
        boSearchBorrowRecordRequest.userId = request.userId;
        boSearchBorrowRecordRequest.bookId = request.bookId;
        boSearchBorrowRecordRequest.skip = request.skip;
        boSearchBorrowRecordRequest.limit = request.limit;

        BOSearchBorrowRecordResponse boSearchBorrowRecordResponse = borrowRecordWebService.search(boSearchBorrowRecordRequest);
        List<BOSearchBorrowRecordResponse.Record> records = boSearchBorrowRecordResponse.records;

        String bookName = bookWebService.get(request.bookId).name;
        Map<Long, String> borrowUserNames = queryBorrowUserNames(records);

        SearchBorrowRecordAJAXResponse response = new SearchBorrowRecordAJAXResponse();
        response.total = boSearchBorrowRecordResponse.total;
        response.records = records.stream()
            .map(record -> {
                SearchBorrowRecordAJAXResponse.Record view = new SearchBorrowRecordAJAXResponse.Record();
                view.id = record.id;
                view.bookName = bookName;
                view.borrowUserId = record.borrowUserId;
                view.borrowUsername = borrowUserNames.get(view.borrowUserId);
                view.borrowedTime = record.borrowedTime;
                view.returnDate = record.returnDate;
                view.actualReturnDate = record.actualReturnDate;
                return view;
            })
            .collect(Collectors.toList());

        return response;
    }

    private Map<Long, String> queryBorrowUserNames(List<BOSearchBorrowRecordResponse.Record> records) {
        List<Long> borrowUserIds = records.stream()
            .map(record -> record.borrowUserId)
            .distinct()
            .collect(Collectors.toList());
        BOSearchUserRequest boSearchUserRequest = new BOSearchUserRequest();
        boSearchUserRequest.ids = borrowUserIds;
        boSearchUserRequest.skip = 0;
        boSearchUserRequest.limit = borrowUserIds.size();
        return userWebService.search(boSearchUserRequest).users.stream()
            .collect(Collectors.toMap(user -> user.id, user -> user.username));
    }
}
