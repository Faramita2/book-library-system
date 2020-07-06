package app.website.book.service;

import app.api.website.book.BookStatusAJAXView;
import app.api.website.book.GetBookAJAXResponse;
import app.api.website.book.SearchBookAJAXRequest;
import app.api.website.book.SearchBookAJAXResponse;
import app.book.api.AuthorWebService;
import app.book.api.BookWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.book.BookStatusView;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.tag.SearchTagRequest;
import app.user.api.UserWebService;
import core.framework.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class BookService {
    @Inject
    BookWebService bookWebService;
    @Inject
    TagWebService tagWebService;
    @Inject
    CategoryWebService categoryWebService;
    @Inject
    AuthorWebService authorWebService;
    @Inject
    UserWebService userWebService;

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
            view.tagNames = queryTagNames(book.tagIds);
            view.description = book.description;
            view.categoryNames = queryCategoryNames(book.categoryIds);
            view.authorNames = queryAuthorNames(book.authorIds);
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
        response.tagNames = queryTagNames(getBookResponse.tags);
        response.categoryNames = queryCategoryNames(getBookResponse.categories);
        response.authorNames = queryAuthorNames(getBookResponse.authors);

        return response;
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
