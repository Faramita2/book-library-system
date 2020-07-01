package app.booksite.book.service;

import app.api.booksite.book.BookStatusAJAXView;
import app.api.booksite.book.CreateBookAJAXRequest;
import app.api.booksite.book.GetBookAJAXResponse;
import app.api.booksite.book.SearchBookAJAXRequest;
import app.api.booksite.book.SearchBookAJAXResponse;
import app.api.booksite.book.UpdateBookAJAXRequest;
import app.book.api.BOAuthorWebService;
import app.book.api.BOBookWebService;
import app.book.api.BOCategoryWebService;
import app.book.api.BOTagWebService;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.category.BOSearchCategoryRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.user.api.BOUserWebService;
import core.framework.inject.Inject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookService {
    @Inject
    BOBookWebService bookWebService;
    @Inject
    BOTagWebService tagWebService;
    @Inject
    BOCategoryWebService categoryWebService;
    @Inject
    BOAuthorWebService authorWebService;
    @Inject
    BOUserWebService userWebService;

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        BOSearchBookRequest req = new BOSearchBookRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        req.tagIds = request.tagIds;
        req.description = request.description;
        req.categoryIds = request.categoryIds;
        req.authorIds = request.authorIds;

        BOSearchBookResponse resp = bookWebService.search(req);
        SearchBookAJAXResponse response = new SearchBookAJAXResponse();

        List<Long> tagIds = distinctTagIds(resp);
        List<Long> categoryIds = distinctCategoryIds(resp);
        List<Long> authorIds = distinctAuthorIds(resp);

        Map<Long, String> tagNames = queryTagNames(tagIds);
        Map<Long, String> categoryNames = queryCategoryNames(categoryIds);
        Map<Long, String> authorNames = queryAuthorNames(authorIds);

        response.total = resp.total;
        response.books = resp.books.stream().map(book -> {
            SearchBookAJAXResponse.Book view = new SearchBookAJAXResponse.Book();
            view.id = book.id;
            view.name = book.name;
            view.description = book.description;
            view.authorNames = book.authorIds.stream().map(authorNames::get).collect(Collectors.toList());
            view.categoryNames = book.categoryIds.stream().map(categoryNames::get).collect(Collectors.toList());
            view.tagNames = book.tagIds.stream().map(tagNames::get).collect(Collectors.toList());
            view.status = BookStatusAJAXView.valueOf(book.status.name());
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetBookAJAXResponse get(Long id) {
        BOGetBookResponse resp = bookWebService.get(id);
        GetBookAJAXResponse response = new GetBookAJAXResponse();

        Map<Long, String> authorNames = queryAuthorNames(resp.authorIds);
        Map<Long, String> categoryNames = queryCategoryNames(resp.categoryIds);
        Map<Long, String> tagNames = queryTagNames(resp.tagIds);

        response.id = resp.id;
        response.name = resp.name;
        response.description = resp.description;
        response.status = BookStatusAJAXView.valueOf(resp.status.name());
        response.borrowerName = resp.borrowerId != 0 ? userWebService.get(resp.borrowerId).username : null;
        response.authorNames = resp.authorIds.stream().map(authorNames::get).collect(Collectors.toList());
        response.categoryNames = resp.categoryIds.stream().map(categoryNames::get).collect(Collectors.toList());
        response.tagNames = resp.tagIds.stream().map(tagNames::get).collect(Collectors.toList());
        response.borrowedAt = resp.borrowedAt;
        response.returnAt = resp.returnAt;

        return response;
    }

    public void create(CreateBookAJAXRequest request) {
        BOCreateBookRequest req = new BOCreateBookRequest();
        req.name = request.name;
        req.tagIds = request.tagIds;
        req.description = request.description;
        req.categoryIds = request.categoryIds;
        req.authorIds = request.authorIds;
        req.operator = "book-site";
        bookWebService.create(req);
    }

    public void update(Long id, UpdateBookAJAXRequest request) {
        BOUpdateBookRequest req = new BOUpdateBookRequest();
        req.name = request.name;
        req.tagIds = request.tagIds;
        req.description = request.description;
        req.categoryIds = request.categoryIds;
        req.authorIds = request.authorIds;
        req.operator = "book-site";
        bookWebService.update(id, req);
    }

    private List<Long> distinctAuthorIds(BOSearchBookResponse resp) {
        return resp.books.stream()
            .map(book -> book.authorIds)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> distinctCategoryIds(BOSearchBookResponse resp) {
        return resp.books.stream()
            .map(book -> book.categoryIds)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> distinctTagIds(BOSearchBookResponse resp) {
        return resp.books.stream()
            .map(book -> book.tagIds)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    private Map<Long, String> queryTagNames(List<Long> tagIds) {
        BOSearchTagRequest boSearchTagRequest = new BOSearchTagRequest();
        boSearchTagRequest.skip = 0;
        boSearchTagRequest.limit = tagIds.size();
        boSearchTagRequest.ids = tagIds;
        return tagWebService.search(boSearchTagRequest).tags.stream()
            .collect(Collectors.toMap(tag -> tag.id, tag -> tag.name));
    }

    private Map<Long, String> queryCategoryNames(List<Long> categoryIds) {
        BOSearchCategoryRequest boSearchCategoryRequest = new BOSearchCategoryRequest();
        boSearchCategoryRequest.skip = 0;
        boSearchCategoryRequest.limit = categoryIds.size();
        boSearchCategoryRequest.ids = categoryIds;
        return categoryWebService.search(boSearchCategoryRequest).categories.stream()
            .collect(Collectors.toMap(category -> category.id, category -> category.name));
    }

    private Map<Long, String> queryAuthorNames(List<Long> authorIds) {
        BOSearchAuthorRequest boSearchAuthorRequest = new BOSearchAuthorRequest();
        boSearchAuthorRequest.skip = 0;
        boSearchAuthorRequest.limit = authorIds.size();
        boSearchAuthorRequest.ids = authorIds;
        return authorWebService.search(boSearchAuthorRequest).authors.stream()
            .collect(Collectors.toMap(author -> author.id, author -> author.name));
    }
}
