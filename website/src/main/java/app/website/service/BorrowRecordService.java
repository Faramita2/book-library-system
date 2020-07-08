package app.website.service;

import app.api.website.borrowrecord.BorrowBookAJAXRequest;
import app.book.api.BookWebService;
import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.GetBookResponse;
import app.book.api.book.UpdateBookRequest;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.BorrowBookRequest;
import app.borrowrecord.api.borrowrecord.GetBorrowRecordResponse;
import app.borrowrecord.api.borrowrecord.ReturnBookRequest;
import core.framework.inject.Inject;
import core.framework.log.Markers;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ForbiddenException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BorrowRecordService {
    @Inject
    BookWebService bookWebService;
    @Inject
    BorrowRecordWebService borrowRecordWebService;

    public void borrowBook(BorrowBookAJAXRequest request, Long userId, String username) {
        GetBookResponse getBookResponse = bookWebService.get(request.bookId);
        if (getBookResponse.status != BookStatusView.AVAILABLE) {
            throw new BadRequestException("cannot borrow this book!", Markers.errorCode("BOOK_BORROWED").getName());
        }
        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        borrowBookRequest.bookId = getBookResponse.id;
        borrowBookRequest.bookName = getBookResponse.name;
        borrowBookRequest.bookDescription = getBookResponse.description;
        borrowBookRequest.authors = getBookResponse.authors.stream().map(this::getAuthorView).collect(Collectors.toList());
        borrowBookRequest.categories = getBookResponse.categories.stream().map(this::getCategoryView).collect(Collectors.toList());
        borrowBookRequest.tags = getBookResponse.tags.stream().map(this::getTagView).collect(Collectors.toList());
        borrowBookRequest.borrowUserId = userId;
        borrowBookRequest.borrowUsername = username;
        borrowBookRequest.returnDate = request.returnDate;
        borrowBookRequest.requestedBy = username;
        borrowRecordWebService.borrowBook(borrowBookRequest);

        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.borrowUserId = userId;
        updateBookRequest.status = BookStatusView.BORROWED;
        updateBookRequest.returnDate = request.returnDate;
        updateBookRequest.borrowedTime = LocalDateTime.now();
        updateBookRequest.requestedBy = username;
        bookWebService.update(request.bookId, updateBookRequest);
    }

    public void returnBook(String id, Long userId, String username) {
        GetBorrowRecordResponse getBorrowRecordResponse = borrowRecordWebService.get(id);
        if (!userId.equals(getBorrowRecordResponse.borrowUserId)) {
            throw new ForbiddenException("You cannot do this.");
        }
        if (getBorrowRecordResponse.actualReturnDate != null) {
            throw new BadRequestException("book has been returned!", Markers.errorCode("BOOK_RETURNED").getName());
        }
        UpdateBookRequest updateBookRequest = new UpdateBookRequest();
        updateBookRequest.borrowUserId = null;
        updateBookRequest.status = BookStatusView.AVAILABLE;
        updateBookRequest.borrowedTime = null;
        updateBookRequest.returnDate = null;
        updateBookRequest.requestedBy = username;
        bookWebService.update(getBorrowRecordResponse.bookId, updateBookRequest);

        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        returnBookRequest.requestedBy = username;
        borrowRecordWebService.returnBook(id, returnBookRequest);
    }

    private BorrowBookRequest.Author getAuthorView(AuthorView author) {
        BorrowBookRequest.Author authorView = new BorrowBookRequest.Author();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }

    private BorrowBookRequest.Category getCategoryView(CategoryView category) {
        BorrowBookRequest.Category categoryView = new BorrowBookRequest.Category();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private BorrowBookRequest.Tag getTagView(TagView tag) {
        BorrowBookRequest.Tag tagView = new BorrowBookRequest.Tag();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }
}
