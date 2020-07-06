package app.booksite.book.service;

import app.api.backoffice.book.BookStatusAJAXView;
import app.api.backoffice.book.CreateBookAJAXRequest;
import app.api.backoffice.book.GetBookAJAXResponse;
import app.api.backoffice.book.SearchBookAJAXRequest;
import app.api.backoffice.book.SearchBookAJAXResponse;
import app.api.backoffice.book.UpdateBookAJAXRequest;
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
    BOBookWebService boBookWebService;
    @Inject
    BOTagWebService boTagWebService;
    @Inject
    BOCategoryWebService boCategoryWebService;
    @Inject
    BOAuthorWebService boAuthorWebService;
    @Inject
    BOUserWebService boUserWebService;

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

        List<Long> tagIds = distinctTagIds(boSearchBookResponse);
        List<Long> categoryIds = distinctCategoryIds(boSearchBookResponse);
        List<Long> authorIds = distinctAuthorIds(boSearchBookResponse);

        Map<Long, String> tagNames = queryTagNames(tagIds);
        Map<Long, String> categoryNames = queryCategoryNames(categoryIds);
        Map<Long, String> authorNames = queryAuthorNames(authorIds);

        response.total = boSearchBookResponse.total;
        response.books = boSearchBookResponse.books.stream().map(book -> {
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
        BOGetBookResponse boGetBookResponse = boBookWebService.get(id);
        GetBookAJAXResponse response = new GetBookAJAXResponse();

        Map<Long, String> authorNames = queryAuthorNames(boGetBookResponse.authorIds);
        Map<Long, String> categoryNames = queryCategoryNames(boGetBookResponse.categoryIds);
        Map<Long, String> tagNames = queryTagNames(boGetBookResponse.tagIds);

        response.id = boGetBookResponse.id;
        response.name = boGetBookResponse.name;
        response.description = boGetBookResponse.description;
        response.status = BookStatusAJAXView.valueOf(boGetBookResponse.status.name());
        response.borrowerName = boGetBookResponse.borrowerId != 0 ? boUserWebService.get(boGetBookResponse.borrowerId).username : null;
        response.authorNames = boGetBookResponse.authorIds.stream().map(authorNames::get).collect(Collectors.toList());
        response.categoryNames = boGetBookResponse.categoryIds.stream().map(categoryNames::get).collect(Collectors.toList());
        response.tagNames = boGetBookResponse.tagIds.stream().map(tagNames::get).collect(Collectors.toList());
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
        boCreateBookRequest.operator = "book-site";
        boBookWebService.create(boCreateBookRequest);
    }

    public void update(Long id, UpdateBookAJAXRequest request) {
        BOUpdateBookRequest boUpdateBookRequest = new BOUpdateBookRequest();
        boUpdateBookRequest.name = request.name;
        boUpdateBookRequest.tagIds = request.tagIds;
        boUpdateBookRequest.description = request.description;
        boUpdateBookRequest.categoryIds = request.categoryIds;
        boUpdateBookRequest.authorIds = request.authorIds;
        boUpdateBookRequest.operator = "book-site";
        boBookWebService.update(id, boUpdateBookRequest);
    }

    private List<Long> distinctAuthorIds(BOSearchBookResponse boSearchBookResponse) {
        return boSearchBookResponse.books.stream()
            .map(book -> book.authorIds)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> distinctCategoryIds(BOSearchBookResponse boSearchBookResponse) {
        return boSearchBookResponse.books.stream()
            .map(book -> book.categoryIds)
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> distinctTagIds(BOSearchBookResponse boSearchBookResponse) {
        return boSearchBookResponse.books.stream()
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
        return boTagWebService.search(boSearchTagRequest).tags.stream()
            .collect(Collectors.toMap(tag -> tag.id, tag -> tag.name));
    }

    private Map<Long, String> queryCategoryNames(List<Long> categoryIds) {
        BOSearchCategoryRequest boSearchCategoryRequest = new BOSearchCategoryRequest();
        boSearchCategoryRequest.skip = 0;
        boSearchCategoryRequest.limit = categoryIds.size();
        boSearchCategoryRequest.ids = categoryIds;
        return boCategoryWebService.search(boSearchCategoryRequest).categories.stream()
            .collect(Collectors.toMap(category -> category.id, category -> category.name));
    }

    private Map<Long, String> queryAuthorNames(List<Long> authorIds) {
        BOSearchAuthorRequest boSearchAuthorRequest = new BOSearchAuthorRequest();
        boSearchAuthorRequest.skip = 0;
        boSearchAuthorRequest.limit = authorIds.size();
        boSearchAuthorRequest.ids = authorIds;
        return boAuthorWebService.search(boSearchAuthorRequest).authors.stream()
            .collect(Collectors.toMap(author -> author.id, author -> author.name));
    }
}
