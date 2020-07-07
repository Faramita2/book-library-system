package app.booksite.book.service;

import app.api.backoffice.book.BookStatusAJAXView;
import app.api.backoffice.book.CreateBookAJAXRequest;
import app.api.backoffice.book.GetBookAJAXResponse;
import app.api.backoffice.book.SearchBookAJAXRequest;
import app.api.backoffice.book.SearchBookAJAXResponse;
import app.api.backoffice.book.UpdateBookAJAXRequest;
import app.api.backoffice.bookauthor.BookAuthorAJAXView;
import app.api.backoffice.bookcategory.BookCategoryAJAXView;
import app.api.backoffice.booktag.BookTagAJAXView;
import app.book.api.BOBookWebService;
import app.book.api.author.AuthorView;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.user.api.BOUserWebService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookService {
    @Inject
    BOBookWebService boBookWebService;
    @Inject
    BOUserWebService boUserWebService;
    @Inject
    WebContext webContext;

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        BOSearchBookRequest boSearchBookRequest = new BOSearchBookRequest();
        boSearchBookRequest.skip = request.skip;
        boSearchBookRequest.limit = request.limit;
        boSearchBookRequest.name = request.name;
        boSearchBookRequest.tagIds = request.tagIds;
        boSearchBookRequest.description = request.description;
        boSearchBookRequest.categoryIds = request.categoryIds;
        boSearchBookRequest.authorIds = request.authorIds;

        BOSearchBookResponse boSearchBookResponse = boBookWebService.search(boSearchBookRequest);
        SearchBookAJAXResponse response = new SearchBookAJAXResponse();

        response.total = boSearchBookResponse.total;
        response.books = boSearchBookResponse.books.stream().map(book -> {
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
        BOGetBookResponse boGetBookResponse = boBookWebService.get(id);
        GetBookAJAXResponse response = new GetBookAJAXResponse();

        response.id = boGetBookResponse.id;
        response.name = boGetBookResponse.name;
        response.description = boGetBookResponse.description;
        response.status = BookStatusAJAXView.valueOf(boGetBookResponse.status.name());
        response.borrowUsername = boGetBookResponse.borrowUserId != 0 ? boUserWebService.get(boGetBookResponse.borrowUserId).username : null;
        response.authors = boGetBookResponse.authors.stream().map(this::bookAuthorAJAXView).collect(Collectors.toList());
        response.categories = boGetBookResponse.categories.stream().map(this::bookCategoryAJAXView).collect(Collectors.toList());
        response.tags = boGetBookResponse.tags.stream().map(this::bookTagAJAXView).collect(Collectors.toList());
        response.borrowedTime = boGetBookResponse.borrowedTime;
        response.returnDate = boGetBookResponse.returnDate;

        return response;
    }

    public void create(CreateBookAJAXRequest request) {
        BOCreateBookRequest boCreateBookRequest = new BOCreateBookRequest();
        boCreateBookRequest.name = request.name;
        boCreateBookRequest.tagIds = request.tagIds;
        boCreateBookRequest.description = request.description;
        boCreateBookRequest.categoryIds = request.categoryIds;
        boCreateBookRequest.authorIds = request.authorIds;
        boCreateBookRequest.requestedBy = adminAccount();

        boBookWebService.create(boCreateBookRequest);
    }

    public void update(Long id, UpdateBookAJAXRequest request) {
        BOUpdateBookRequest boUpdateBookRequest = new BOUpdateBookRequest();
        boUpdateBookRequest.name = request.name;
        boUpdateBookRequest.tagIds = request.tagIds;
        boUpdateBookRequest.description = request.description;
        boUpdateBookRequest.categoryIds = request.categoryIds;
        boUpdateBookRequest.authorIds = request.authorIds;
        boUpdateBookRequest.requestedBy = adminAccount();

        boBookWebService.update(id, boUpdateBookRequest);
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

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
