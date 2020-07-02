package app.booksite.borrowrecord.service;

import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.booksite.borrowrecord.SearchBorrowRecordAJAXResponse;
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
        boSearchBorrowRecordRequest.bookId = request.bookId;
        boSearchBorrowRecordRequest.skip = request.skip;
        boSearchBorrowRecordRequest.limit = request.limit;

        BOSearchBorrowRecordResponse boSearchBorrowRecordResponse = borrowRecordWebService.search(boSearchBorrowRecordRequest);
        List<BOSearchBorrowRecordResponse.Record> records = boSearchBorrowRecordResponse.records;

        String bookName = bookWebService.get(request.bookId).name;
        Map<Long, String> borrowerNames = queryBorrowerNames(records);

        SearchBorrowRecordAJAXResponse response = new SearchBorrowRecordAJAXResponse();
        response.total = boSearchBorrowRecordResponse.total;
        response.records = records.stream()
            .map(record -> {
                SearchBorrowRecordAJAXResponse.Record view = new SearchBorrowRecordAJAXResponse.Record();
                view.id = record.id;
                view.bookName = bookName;
                view.borrowerId = record.borrowerId;
                view.borrowerName = borrowerNames.get(view.borrowerId);
                view.borrowedAt = record.borrowedAt;
                view.returnAt = record.returnAt;
                return view;
            })
            .collect(Collectors.toList());

        return response;
    }

    private Map<Long, String> queryBorrowerNames(List<BOSearchBorrowRecordResponse.Record> records) {
        List<Long> borrowerIds = records.stream()
            .map(record -> record.borrowerId)
            .distinct()
            .collect(Collectors.toList());
        BOSearchUserRequest boSearchUserRequest = new BOSearchUserRequest();
        boSearchUserRequest.ids = borrowerIds;
        boSearchUserRequest.skip = 0;
        boSearchUserRequest.limit = borrowerIds.size();
        return userWebService.search(boSearchUserRequest).users.stream()
            .collect(Collectors.toMap(user -> user.id, user -> user.username));
    }
}
