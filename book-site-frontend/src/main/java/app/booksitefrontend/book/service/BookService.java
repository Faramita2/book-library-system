package app.booksitefrontend.book.service;

import app.api.booksitefrontend.book.BookStatusAJAXView;
import app.api.booksitefrontend.book.BorrowBookAJAXRequest;
import app.api.booksitefrontend.book.GetBookAJAXResponse;
import app.api.booksitefrontend.book.SearchBookAJAXRequest;
import app.api.booksitefrontend.book.SearchBookAJAXResponse;
import app.api.booksitefrontend.book.SearchBorrowedBookAJAXRequest;
import app.api.booksitefrontend.book.SearchBorrowedBookAJAXResponse;
import app.book.api.AuthorWebService;
import app.book.api.BookWebService;
import app.book.api.CategoryWebService;
import app.book.api.TagWebService;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.book.BookStatusView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.category.SearchCategoryRequest;
import app.book.api.tag.SearchTagRequest;
import app.user.api.UserWebService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

import java.util.List;
import java.util.Optional;
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
    WebContext webContext;
    @Inject
    UserWebService userWebService;

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        SearchBookRequest req = new SearchBookRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        req.description = request.description;
        req.tagIds = request.tagIds;
        req.authorIds = request.authorIds;
        req.categoryIds = request.categoryIds;

        if (req.status != null) {
            BookStatusView.valueOf(request.status.name());
        }
        SearchBookResponse resp = bookWebService.search(req);
        SearchBookAJAXResponse response = new SearchBookAJAXResponse();

        response.total = resp.total;
        response.books = resp.books.stream().map(book -> {
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

    public SearchBorrowedBookAJAXResponse searchBorrowed(SearchBorrowedBookAJAXRequest request) {
        SearchBookRequest req = new SearchBookRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        req.description = request.description;
        req.tagIds = request.tagIds;
        req.authorIds = request.authorIds;
        req.categoryIds = request.categoryIds;
        req.status = BookStatusView.BORROWED;
        req.borrowerId = Long.valueOf(webContext.request().session().get("user_id").orElse(String.valueOf(0)));

        SearchBookResponse resp = bookWebService.search(req);
        SearchBorrowedBookAJAXResponse response = new SearchBorrowedBookAJAXResponse();

        response.total = resp.total;
        response.books = resp.books.stream().map(book -> {
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

    private List<String> queryTagNames(List<Long> tagIds) {
        SearchTagRequest SearchTagRequest = new SearchTagRequest();
        SearchTagRequest.skip = 0;
        SearchTagRequest.limit = tagIds.size();
        SearchTagRequest.ids = tagIds;
        return tagWebService.search(SearchTagRequest).tags.stream()
            .map(tag -> tag.name)
            .collect(Collectors.toList());
    }

    private List<String> queryCategoryNames(List<Long> categoryIds) {
        SearchCategoryRequest SearchCategoryRequest = new SearchCategoryRequest();
        SearchCategoryRequest.skip = 0;
        SearchCategoryRequest.limit = categoryIds.size();
        SearchCategoryRequest.ids = categoryIds;
        return categoryWebService.search(SearchCategoryRequest).categories.stream()
            .map(category -> category.name)
            .collect(Collectors.toList());
    }

    private List<String> queryAuthorNames(List<Long> authorIds) {
        SearchAuthorRequest SearchAuthorRequest = new SearchAuthorRequest();
        SearchAuthorRequest.skip = 0;
        SearchAuthorRequest.limit = authorIds.size();
        SearchAuthorRequest.ids = authorIds;
        return authorWebService.search(SearchAuthorRequest).authors.stream()
            .map(author -> author.name)
            .collect(Collectors.toList());
    }

    public GetBookAJAXResponse get(Long id) {
        GetBookResponse resp = bookWebService.get(id);
        GetBookAJAXResponse response = new GetBookAJAXResponse();

        response.id = resp.id;
        response.name = resp.name;
        response.description = resp.description;
        response.status = BookStatusAJAXView.valueOf(resp.status.name());
        response.borrowerName = resp.borrowerId != 0 ? userWebService.get(resp.borrowerId).username : null;
        response.borrowedAt = resp.borrowedAt;
        response.returnAt = resp.returnAt;
        response.tagNames = queryTagNames(resp.tagIds);
        response.categoryNames = queryCategoryNames(resp.categoryIds);
        response.authorNames = queryAuthorNames(resp.authorIds);

        return response;
    }

    public void borrow(Long id, BorrowBookAJAXRequest request) {
        BorrowBookRequest req = new BorrowBookRequest();
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow();
        req.userId = Long.valueOf(userId.get());
        req.operator = "book-site-frontend";
        req.returnAt = request.returnAt;
        bookWebService.borrow(id, req);
    }

    public void returnBook(Long id) {
        ReturnBookRequest req = new ReturnBookRequest();
        Optional<String> userId = webContext.request().session().get("user_id");
        userId.orElseThrow();
        req.userId = Long.valueOf(userId.get());
        req.operator = "book-site-frontend";
        bookWebService.returnBook(id, req);
    }
}
