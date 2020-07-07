package app.website.borrowrecord.service;

import app.api.website.borrowrecord.BorrowBookAJAXRequest;
import app.book.api.BookWebService;
import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.GetBookResponse;
import app.book.api.book.UpdateBookRequest;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.CreateBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.UpdateBorrowRecordRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BookWebService bookWebService;
    @Inject
    BorrowRecordWebService borrowRecordWebService;
    @Inject
    WebContext webContext;

    public void borrowBook(BorrowBookAJAXRequest request) {
        GetBookResponse getBookResponse = bookWebService.get(request.bookId);
        CreateBorrowRecordRequest createBorrowRecordRequest = new CreateBorrowRecordRequest();
        createBorrowRecordRequest.bookId = getBookResponse.id;
        createBorrowRecordRequest.bookName = getBookResponse.name;
        createBorrowRecordRequest.bookDescription = getBookResponse.description;
        createBorrowRecordRequest.authors = getBookResponse.authors.stream().map(this::getAuthorView).collect(Collectors.toList());
        createBorrowRecordRequest.categories = getBookResponse.categories.stream().map(this::getCategoryView).collect(Collectors.toList());
        createBorrowRecordRequest.tags = getBookResponse.tags.stream().map(this::getTagView).collect(Collectors.toList());
        createBorrowRecordRequest.borrowUserId = getBookResponse.borrowUserId;
        createBorrowRecordRequest.borrowedTime = getBookResponse.borrowedTime;
        createBorrowRecordRequest.returnDate = getBookResponse.returnDate;
        createBorrowRecordRequest.operator = getUsername();
        borrowRecordWebService.create(createBorrowRecordRequest);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.userId = getUserId();
        updateBookRequest.status = BookStatusView.BORROWED;
        updateBookRequest.returnDate = request.returnDate;
        updateBookRequest.requestedBy = getUsername();
        bookWebService.update(request.bookId, updateBookRequest);
    }

    public void returnBook(String id) {
        GetBorrowRecordResponse getBorrowRecordResponse = borrowRecordWebService.get(id);
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.userId = getUserId();
        updateBookRequest.status = BookStatusView.AVAILABLE;
        updateBookRequest.returnDate = null;
        updateBookRequest.requestedBy = getUsername();
        bookWebService.update(getBorrowRecordResponse.bookId, updateBookRequest);

        UpdateBorrowRecordRequest updateBorrowRecordRequest = new UpdateBorrowRecordRequest();
        updateBorrowRecordRequest.actualReturnDate = LocalDate.now();
        updateBorrowRecordRequest.requestedBy = getUsername();
        borrowRecordWebService.update(id, updateBorrowRecordRequest);
    }

    private CreateBorrowRecordRequest.Author getAuthorView(AuthorView author) {
        CreateBorrowRecordRequest.Author authorView = new CreateBorrowRecordRequest.Author();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }

    private CreateBorrowRecordRequest.Category getCategoryView(CategoryView category) {
        CreateBorrowRecordRequest.Category categoryView = new CreateBorrowRecordRequest.Category();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private CreateBorrowRecordRequest.Tag getTagView(TagView tag) {
        CreateBorrowRecordRequest.Tag tagView = new CreateBorrowRecordRequest.Tag();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }

    private Long getUserId() {
        String userId = webContext.request().session().get("user_id").orElseThrow(() ->
            new UnauthorizedException("please login first."));
        return Long.parseLong(userId);
    }

    private String getUsername() {
        return webContext.request().session().get("username").orElseThrow(() ->
            new UnauthorizedException("please login first."));
    }
}
