package app.backoffice.service;

import app.api.backoffice.bookauthor.BookAuthorAJAXView;
import app.api.backoffice.bookcategory.BookCategoryAJAXView;
import app.api.backoffice.booktag.BookTagAJAXView;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXRequest;
import app.api.backoffice.borrowrecord.SearchBorrowRecordAJAXResponse;
import app.book.api.BOBorrowRecordWebService;
import app.book.api.borrowrecord.BOSearchBorrowRecordRequest;
import app.book.api.borrowrecord.BOSearchBorrowRecordResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BOBorrowRecordWebService boBorrowRecordWebService;

    public SearchBorrowRecordAJAXResponse search(SearchBorrowRecordAJAXRequest request) {
        BOSearchBorrowRecordRequest boSearchBorrowRecordRequest = new BOSearchBorrowRecordRequest();
        boSearchBorrowRecordRequest.userId = request.userId;
        boSearchBorrowRecordRequest.bookId = request.bookId;
        boSearchBorrowRecordRequest.skip = request.skip;
        boSearchBorrowRecordRequest.limit = request.limit;
        BOSearchBorrowRecordResponse boSearchBorrowRecordResponse = boBorrowRecordWebService.search(boSearchBorrowRecordRequest);

        SearchBorrowRecordAJAXResponse response = new SearchBorrowRecordAJAXResponse();
        response.total = boSearchBorrowRecordResponse.total;
        response.records = boSearchBorrowRecordResponse.records.stream().map(record -> {
            SearchBorrowRecordAJAXResponse.User userView = new SearchBorrowRecordAJAXResponse.User();
            userView.id = record.user.id;
            userView.username = record.user.username;

            SearchBorrowRecordAJAXResponse.Book bookView = new SearchBorrowRecordAJAXResponse.Book();
            bookView.id = record.book.id;
            bookView.name = record.book.name;
            bookView.description = record.book.description;
            bookView.authors = record.book.authors.stream().map(this::bookAuthorAJAXView).collect(Collectors.toList());
            bookView.categories = record.book.categories.stream().map(this::bookCategoryAJAXView).collect(Collectors.toList());
            bookView.tags = record.book.tags.stream().map(this::bookTagAJAXView).collect(Collectors.toList());

            SearchBorrowRecordAJAXResponse.Record recordView = new SearchBorrowRecordAJAXResponse.Record();
            recordView.id = record.id;
            recordView.user = userView;
            recordView.book = bookView;
            recordView.borrowedTime = record.borrowedTime;
            recordView.returnDate = record.returnDate;
            recordView.actualReturnDate = record.actualReturnDate;
            return recordView;
        }).collect(Collectors.toList());

        return response;
    }

    private BookTagAJAXView bookTagAJAXView(BOSearchBorrowRecordResponse.Tag tag) {
        BookTagAJAXView bookTagAJAXView = new BookTagAJAXView();
        bookTagAJAXView.id = tag.id;
        bookTagAJAXView.name = tag.name;
        return bookTagAJAXView;
    }

    private BookCategoryAJAXView bookCategoryAJAXView(BOSearchBorrowRecordResponse.Category category) {
        BookCategoryAJAXView bookCategoryAJAXView = new BookCategoryAJAXView();
        bookCategoryAJAXView.id = category.id;
        bookCategoryAJAXView.name = category.name;
        return bookCategoryAJAXView;
    }

    private BookAuthorAJAXView bookAuthorAJAXView(BOSearchBorrowRecordResponse.Author author) {
        BookAuthorAJAXView bookAuthorAJAXView = new BookAuthorAJAXView();
        bookAuthorAJAXView.id = author.id;
        bookAuthorAJAXView.name = author.name;
        return bookAuthorAJAXView;
    }
}
