package app.website.borrowedbook.service;

import app.api.website.borrowedbook.SearchBorrowedBookAJAXRequest;
import app.api.website.borrowedbook.SearchBorrowedBookAJAXResponse;
import app.book.api.AuthorWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.tag.SearchTagRequest;
import app.borrowrecord.api.BorrowRecordWebService;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordRequest;
import app.borrowrecord.api.borrowrecord.SearchBorrowRecordResponse;
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
    BorrowRecordWebService borrowRecordWebService;
    @Inject
    WebContext webContext;
    @Inject
    TagWebService tagWebService;
    @Inject
    AuthorWebService authorWebService;
    @Inject
    CategoryWebService categoryWebService;

    public SearchBorrowedBookAJAXResponse search(SearchBorrowedBookAJAXRequest request) {
        SearchBorrowRecordRequest searchBorrowRecordRequest = new SearchBorrowRecordRequest();
        searchBorrowRecordRequest.skip = request.skip;
        searchBorrowRecordRequest.limit = request.limit;
        searchBorrowRecordRequest.bookName = request.name;
        searchBorrowRecordRequest.bookDescription = request.description;
        searchBorrowRecordRequest.tagIds = request.tagIds;
        searchBorrowRecordRequest.authorIds = request.authorIds;
        searchBorrowRecordRequest.categoryIds = request.categoryIds;
        searchBorrowRecordRequest.borrowUserId = Long.valueOf(getUserId());
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
                SearchBorrowedBookAJAXResponse.Author authorView = new SearchBorrowedBookAJAXResponse.Author();
                authorView.id = author.id;
                authorView.name = author.name;
                return authorView;
            }).collect(Collectors.toList());
            view.categories = record.book.categories.stream().map(category -> {
                SearchBorrowedBookAJAXResponse.Category categoryView = new SearchBorrowedBookAJAXResponse.Category();
                categoryView.id = category.id;
                categoryView.name = category.name;
                return categoryView;
            }).collect(Collectors.toList());
            view.tags = record.book.tags.stream().map(tag -> {
                SearchBorrowedBookAJAXResponse.Tag tagView = new SearchBorrowedBookAJAXResponse.Tag();
                tagView.id = tag.id;
                tagView.name = tag.name;
                return tagView;
            }).collect(Collectors.toList());
            view.borrowedTime = record.borrowedTime;
            view.returnDate = record.returnDate;
            view.actualReturnDate = record.actualReturnDate;
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
