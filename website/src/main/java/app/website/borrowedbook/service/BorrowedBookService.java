package app.website.borrowedbook.service;

import app.api.website.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.website.borrowedbook.SearchBorrowedBookAJAXResponse;
import app.book.api.AuthorWebService;
import app.book.api.BookWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.book.BookStatusView;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.tag.SearchTagRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BorrowedBookService {
    @Inject
    BookWebService bookWebService;
    @Inject
    WebContext webContext;
    @Inject
    TagWebService tagWebService;
    @Inject
    AuthorWebService authorWebService;
    @Inject
    CategoryWebService categoryWebService;

    public SearchBorrowedBookAJAXResponse search(SearchBorrowedBookAJAXRequest request) {
        SearchBookRequest searchBookRequest = new SearchBookRequest();
        searchBookRequest.skip = request.skip;
        searchBookRequest.limit = request.limit;
        searchBookRequest.name = request.name;
        searchBookRequest.description = request.description;
        searchBookRequest.tagIds = request.tagIds;
        searchBookRequest.authorIds = request.authorIds;
        searchBookRequest.categoryIds = request.categoryIds;
        searchBookRequest.status = BookStatusView.BORROWED;
        searchBookRequest.borrowUserId = Long.valueOf(getUserId());

        SearchBookResponse searchBookResponse = bookWebService.search(searchBookRequest);
        SearchBorrowedBookAJAXResponse response = new SearchBorrowedBookAJAXResponse();

        response.total = searchBookResponse.total;
        response.books = searchBookResponse.books.stream().map(book -> {
            SearchBorrowedBookAJAXResponse.Book view = new SearchBorrowedBookAJAXResponse.Book();
            view.id = book.id;
            view.name = book.name;
            view.tagNames = queryTagNames(book.tagIds);
            view.description = book.description;
            view.categoryNames = queryCategoryNames(book.categoryIds);
            view.authorNames = queryAuthorNames(book.authorIds);
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    private String getUserId() {
        return webContext.request().session().get("user_id").orElseThrow(() ->
            new UnauthorizedException("please login first."));
    }

    private List<String> queryTagNames(List<Long> tagIds) {
        SearchTagRequest searchTagRequest = new SearchTagRequest();
        searchTagRequest.skip = 0;
        searchTagRequest.limit = tagIds.size();
        searchTagRequest.ids = tagIds;
        return tagWebService.search(searchTagRequest).tags.stream()
            .map(tag -> tag.name)
            .collect(Collectors.toList());
    }

    private List<String> queryCategoryNames(List<Long> categoryIds) {
        SearchCategoryRequest searchCategoryRequest = new SearchCategoryRequest();
        searchCategoryRequest.skip = 0;
        searchCategoryRequest.limit = categoryIds.size();
        searchCategoryRequest.ids = categoryIds;
        return categoryWebService.search(searchCategoryRequest).categories.stream()
            .map(category -> category.name)
            .collect(Collectors.toList());
    }

    private List<String> queryAuthorNames(List<Long> authorIds) {
        SearchAuthorRequest searchAuthorRequest = new SearchAuthorRequest();
        searchAuthorRequest.skip = 0;
        searchAuthorRequest.limit = authorIds.size();
        searchAuthorRequest.ids = authorIds;
        return authorWebService.search(searchAuthorRequest).authors.stream()
            .map(author -> author.name)
            .collect(Collectors.toList());
    }
}
