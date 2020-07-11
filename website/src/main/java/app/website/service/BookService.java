package app.website.service;

import app.book.api.BookWebService;
import app.book.api.BorrowRecordWebService;
import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.borrowrecord.SearchBorrowRecordRequest;
import app.book.api.borrowrecord.SearchBorrowRecordResponse;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.user.api.UserWebService;
import app.website.api.book.BookStatusAJAXView;
import app.website.api.book.GetBookAJAXResponse;
import app.website.api.book.SearchBookAJAXRequest;
import app.website.api.book.SearchBookAJAXResponse;
import app.website.api.book.SearchBorrowedBookAJAXRequest;
import app.website.api.book.SearchBorrowedBookAJAXResponse;
import app.website.api.bookauthor.BookAuthorAJAXView;
import app.website.api.bookcategory.BookCategoryAJAXView;
import app.website.api.booktag.BookTagAJAXView;
import app.website.api.borrowrecord.BorrowBookAJAXRequest;
import core.framework.inject.Inject;
import core.framework.web.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BookService {
    @Inject
    BookWebService bookWebService;
    @Inject
    UserWebService userWebService;
    @Inject
    BorrowRecordWebService borrowRecordWebService;

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        SearchBookRequest searchBookRequest = new SearchBookRequest();
        searchBookRequest.skip = request.skip;
        searchBookRequest.limit = request.limit;
        searchBookRequest.name = request.name;
        searchBookRequest.description = request.description;
        searchBookRequest.tagIds = request.tagIds;
        searchBookRequest.authorIds = request.authorIds;
        searchBookRequest.categoryIds = request.categoryIds;
        if (request.status != null) {
            searchBookRequest.status = BookStatusView.valueOf(request.status.name());
        }
        SearchBookResponse searchBookResponse = bookWebService.search(searchBookRequest);

        SearchBookAJAXResponse response = new SearchBookAJAXResponse();
        response.total = searchBookResponse.total;
        response.books = searchBookResponse.books.stream().map(book -> {
            SearchBookAJAXResponse.Book view = new SearchBookAJAXResponse.Book();
            view.id = book.id;
            view.name = book.name;
            view.description = book.description;
            view.authors = book.authors.stream().map(this::bookAuthorAJAXView).collect(Collectors.toList());
            view.categories = book.categories.stream().map(this::bookCategoryAJAXView).collect(Collectors.toList());
            view.tags = book.tags.stream().map(this::bookTagAJAXView).collect(Collectors.toList());
            view.status = BookStatusAJAXView.valueOf(book.status.name());
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetBookAJAXResponse get(Long id) {
        GetBookResponse getBookResponse = bookWebService.get(id);

        GetBookAJAXResponse response = new GetBookAJAXResponse();
        response.id = getBookResponse.id;
        response.name = getBookResponse.name;
        response.description = getBookResponse.description;
        response.status = BookStatusAJAXView.valueOf(getBookResponse.status.name());
        response.borrowUsername = getBookResponse.borrowUserId != null ? userWebService.get(getBookResponse.borrowUserId).username : null;
        response.borrowedTime = getBookResponse.borrowedTime;
        response.returnDate = getBookResponse.returnDate;
        response.authors = getBookResponse.authors.stream().map(this::bookAuthorAJAXView).collect(Collectors.toList());
        response.categories = getBookResponse.categories.stream().map(this::bookCategoryAJAXView).collect(Collectors.toList());
        response.tags = getBookResponse.tags.stream().map(this::bookTagAJAXView).collect(Collectors.toList());
        response.status = BookStatusAJAXView.valueOf(getBookResponse.status.name());

        return response;
    }

    public void borrow(Long id, BorrowBookAJAXRequest request, Long userId, String username) {
        if (request.returnDate.atStartOfDay().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new BadRequestException("book return date error!", "ERROR_BOOK_RETURN_DATE");
        }
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        borrowBookRequest.borrowUserId = userId;
        borrowBookRequest.borrowUsername = username;
        borrowBookRequest.returnDate = request.returnDate;
        borrowBookRequest.requestedBy = username;
        bookWebService.borrow(id, borrowBookRequest);
    }

    public SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request, Long userId) {
        SearchBorrowRecordRequest searchBorrowRecordRequest = new SearchBorrowRecordRequest();
        searchBorrowRecordRequest.skip = request.skip;
        searchBorrowRecordRequest.limit = request.limit;
        searchBorrowRecordRequest.borrowUserId = userId;
        SearchBorrowRecordResponse searchBorrowRecordResponse = borrowRecordWebService.search(searchBorrowRecordRequest);

        SearchBorrowedBookAJAXResponse response = new SearchBorrowedBookAJAXResponse();
        response.total = searchBorrowRecordResponse.total;
        response.books = searchBorrowRecordResponse.records.stream().map(record -> {
            SearchBorrowedBookAJAXResponse.Book view = new SearchBorrowedBookAJAXResponse.Book();
            view.id = record.book.id;
            view.recordId = record.id;
            view.name = record.book.name;
            view.description = record.book.description;
            view.authors = record.book.authors.stream().map(author -> {
                BookAuthorAJAXView bookAuthorAJAXView = new BookAuthorAJAXView();
                bookAuthorAJAXView.id = author.id;
                bookAuthorAJAXView.name = author.name;
                return bookAuthorAJAXView;
            }).collect(Collectors.toList());
            view.categories = record.book.categories.stream().map(category -> {
                BookCategoryAJAXView bookCategoryAJAXView = new BookCategoryAJAXView();
                bookCategoryAJAXView.id = category.id;
                bookCategoryAJAXView.name = category.name;
                return bookCategoryAJAXView;
            }).collect(Collectors.toList());
            view.tags = record.book.tags.stream().map(tag -> {
                BookTagAJAXView bookTagAJAXView = new BookTagAJAXView();
                bookTagAJAXView.id = tag.id;
                bookTagAJAXView.name = tag.name;
                return bookTagAJAXView;
            }).collect(Collectors.toList());
            view.borrowedTime = record.borrowedTime;
            view.returnDate = record.returnDate;
            view.actualReturnDate = record.actualReturnDate;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    private BookAuthorAJAXView bookAuthorAJAXView(AuthorView author) {
        BookAuthorAJAXView bookAuthorAJAXView = new BookAuthorAJAXView();
        bookAuthorAJAXView.id = author.id;
        bookAuthorAJAXView.name = author.name;
        return bookAuthorAJAXView;
    }

    private BookCategoryAJAXView bookCategoryAJAXView(CategoryView category) {
        BookCategoryAJAXView bookCategoryAJAXView = new BookCategoryAJAXView();
        bookCategoryAJAXView.id = category.id;
        bookCategoryAJAXView.name = category.name;
        return bookCategoryAJAXView;
    }

    private BookTagAJAXView bookTagAJAXView(TagView tag) {
        BookTagAJAXView bookTagAJAXView = new BookTagAJAXView();
        bookTagAJAXView.id = tag.id;
        bookTagAJAXView.name = tag.name;
        return bookTagAJAXView;
    }
}
