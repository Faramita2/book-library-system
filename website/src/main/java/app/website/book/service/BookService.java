package app.website.book.service;

import app.api.website.book.BookStatusAJAXView;
import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.api.website.book.SearchBorrowedBookAJAXRequest;
import app.api.website.book.SearchBorrowedBookAJAXResponse;
import app.api.website.bookauthor.BookAuthorAJAXView;
import app.api.website.bookcategory.BookCategoryAJAXView;
import app.api.website.booktag.BookTagAJAXView;
import app.book.api.BookWebService;
import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
import app.user.api.UserWebService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

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
    @Inject
    WebContext webContext;

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

    public SearchBorrowedBookAJAXResponse searchBorrowedBook(SearchBorrowedBookAJAXRequest request) {
        SearchBorrowRecordRequest searchBorrowRecordRequest = new SearchBorrowRecordRequest();
        searchBorrowRecordRequest.skip = request.skip;
        searchBorrowRecordRequest.limit = request.limit;
        searchBorrowRecordRequest.bookName = request.name;
        searchBorrowRecordRequest.bookDescription = request.description;
        searchBorrowRecordRequest.tagIds = request.tagIds;
        searchBorrowRecordRequest.authorIds = request.authorIds;
        searchBorrowRecordRequest.categoryIds = request.categoryIds;
        searchBorrowRecordRequest.borrowUserId = getUserId();
        searchBorrowRecordRequest.borrowedDate = request.borrowedDate;
        searchBorrowRecordRequest.returnDate = request.returnDate;
        searchBorrowRecordRequest.actualReturnDate = request.actualReturnDate;
        SearchBorrowRecordResponse searchBorrowRecordResponse = borrowRecordWebService.search(searchBorrowRecordRequest);

        SearchBorrowedBookAJAXResponse response = new SearchBorrowedBookAJAXResponse();
        response.total = searchBorrowRecordResponse.total;
        response.books = searchBorrowRecordResponse.records.stream().map(record -> {
            SearchBorrowedBookAJAXResponse.Book view = new SearchBorrowedBookAJAXResponse.Book();
            view.id = record.book.id;
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

    private Long getUserId() {
        String userId = webContext.request().session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
        return Long.parseLong(userId);
    }
}
