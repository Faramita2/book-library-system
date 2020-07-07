package app.book.book.service;

import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.UpdateBookRequest;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.book.author.domain.Author;
import app.book.book.domain.Book;
import app.book.book.domain.BookAuthor;
import app.book.book.domain.BookCategory;
import app.book.book.domain.BookStatus;
import app.book.book.domain.BookTag;
import app.book.category.domain.Category;
import app.book.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Repository<BookAuthor> bookAuthorRepository;
    @Inject
    Repository<BookCategory> bookCategoryRepository;
    @Inject
    Repository<BookTag> bookTagRepository;
    @Inject
    Repository<Author> authorRepository;
    @Inject
    Repository<Category> categoryRepository;
    @Inject
    Repository<Tag> tagRepository;

    public SearchBookResponse search(SearchBookRequest request) {
        Query<Book> query = bookRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);

        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        if (!Strings.isBlank(request.description)) {
            query.where("description LIKE ?", Strings.format("{}%", request.description));
        }

        if (request.authorIds != null && !request.authorIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByAuthorIds(request);
            query.in("id", bookIds);
        }

        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByCategoryIds(request);
            query.in("id", bookIds);
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            List<Long> bookIds = queryBookIdsByTagIds(request);
            query.in("id", bookIds);
        }

        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }

        SearchBookResponse response = new SearchBookResponse();
        response.total = query.count();
        response.books = query.fetch().stream().map(book -> {
            SearchBookResponse.Book view = new SearchBookResponse.Book();
            view.id = book.id;
            view.name = book.name;
            view.description = book.description;
            view.authors = queryAuthorsByBookId(book.id).stream().map(this::authorView).collect(Collectors.toList());
            view.categories = queryCategoriesByBookId(book.id).stream().map(this::categoryView).collect(Collectors.toList());
            view.tags = queryTagsByBookId(book.id).stream().map(this::tagView).collect(Collectors.toList());
            view.status = BookStatusView.valueOf(book.status.name());
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public GetBookResponse get(Long id) {
        Book book = bookRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));

        GetBookResponse response = new GetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.description = book.description;
        response.authors = queryAuthorsByBookId(id).stream().map(this::authorView).collect(Collectors.toList());
        response.categories = queryCategoriesByBookId(id).stream().map(this::categoryView).collect(Collectors.toList());
        response.tags = queryTagsByBookId(id).stream().map(this::tagView).collect(Collectors.toList());
        response.status = BookStatusView.valueOf(book.status.name());
        response.borrowUserId = book.borrowUserId;
        response.borrowedTime = book.borrowedTime;
        response.returnDate = book.returnDate;

        return response;
    }

    public void update(Long id, UpdateBookRequest request) {
        Book book = bookRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("book not found, id = {}", id)));
        book.status = BookStatus.valueOf(request.status.name());
        book.borrowUserId = request.borrowUserId;
        book.borrowedTime = request.borrowedTime;
        book.returnDate = request.returnDate;
        book.updatedTime = LocalDateTime.now();
        book.updatedBy = request.requestedBy;

        bookRepository.update(book);
    }

    private List<Author> queryAuthorsByBookId(Long bookId) {
        List<Long> authorIds = bookAuthorRepository.select("book_id = ?", bookId).stream().map(bookAuthor -> bookAuthor.authorId).collect(Collectors.toList());
        Query<Author> query = authorRepository.select();
        query.in("id", authorIds);
        return query.fetch();
    }

    private List<Category> queryCategoriesByBookId(Long bookId) {
        List<Long> categoryIds = bookCategoryRepository.select("book_id = ?", bookId).stream().map(bookCategory -> bookCategory.categoryId).collect(Collectors.toList());
        Query<Category> query = categoryRepository.select();
        query.in("id", categoryIds);
        return query.fetch();
    }

    private List<Tag> queryTagsByBookId(Long bookId) {
        List<Long> tagIds = bookTagRepository.select("book_id = ?", bookId).stream().map(bookTag -> bookTag.tagId).collect(Collectors.toList());
        Query<Tag> query = tagRepository.select();
        query.in("id", tagIds);
        return query.fetch();
    }

    private List<Long> queryBookIdsByAuthorIds(SearchBookRequest request) {
        Query<BookTag> query = bookTagRepository.select();
        query.in("tag_id", request.tagIds);
        return query.fetch().stream().map(bookTag -> bookTag.bookId).collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByCategoryIds(SearchBookRequest request) {
        Query<BookCategory> query = bookCategoryRepository.select();
        query.in("category_id", request.categoryIds);
        return query.fetch().stream().map(bookCategory -> bookCategory.bookId).collect(Collectors.toList());
    }

    private List<Long> queryBookIdsByTagIds(SearchBookRequest request) {
        Query<BookTag> query = bookTagRepository.select();
        query.in("tag_id", request.tagIds);
        return query.fetch().stream().map(bookTag -> bookTag.bookId).collect(Collectors.toList());
    }

    private AuthorView authorView(Author author) {
        AuthorView authorView = new AuthorView();
        authorView.id = author.id;
        authorView.name = author.name;
        return authorView;
    }

    private CategoryView categoryView(Category category) {
        CategoryView categoryView = new CategoryView();
        categoryView.id = category.id;
        categoryView.name = category.name;
        return categoryView;
    }

    private TagView tagView(Tag tag) {
        TagView tagView = new TagView();
        tagView.id = tag.id;
        tagView.name = tag.name;
        return tagView;
    }
}
