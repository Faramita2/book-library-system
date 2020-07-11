package app.book.book.service;

import app.book.api.author.AuthorView;
import app.book.api.book.BookStatusView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.category.CategoryView;
import app.book.api.tag.TagView;
import app.book.author.domain.Author;
import app.book.book.domain.Book;
import app.book.book.domain.BookAuthor;
import app.book.book.domain.BookCategory;
import app.book.book.domain.BookStatus;
import app.book.book.domain.BookTag;
import app.book.borrowrecord.domain.BorrowRecord;
import app.book.category.domain.Category;
import app.book.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.bson.types.ObjectId;

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
    @Inject
    MongoCollection<BorrowRecord> borrowRecordMongoCollection;

    public SearchBookResponse search(SearchBookRequest request) {
        Query<Book> query = bookRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);

        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("%{}%", request.name));
        }

        if (!Strings.isBlank(request.description)) {
            query.where("description LIKE ?", Strings.format("%{}%", request.description));
        }

        if (request.authorIds != null && !request.authorIds.isEmpty()) {
            query.where(Strings.format("id IN(SELECT book_id from book_authors WHERE author_id IN({}))"),
                request.authorIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            query.where(Strings.format("id IN(SELECT book_id from book_categories WHERE category_id IN({}))"),
                request.categoryIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        if (request.tagIds != null && !request.tagIds.isEmpty()) {
            query.where(Strings.format("id IN(SELECT book_id from book_tags WHERE tag_id IN({}))",
                request.tagIds.stream().map(String::valueOf).collect(Collectors.joining(","))));
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
        Book book = bookRepository.get(id).orElseThrow(() -> new NotFoundException(
            Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));

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

    public void borrow(Long id, BorrowBookRequest request) {
        synchronized (this) {
            Book book = bookRepository.get(id).orElseThrow(() -> new NotFoundException(
                Strings.format("book not found, id = {}", id), "BOOK_NOT_FOUND"));
            if (book.status == BookStatus.BORROWED) {
                throw new BadRequestException(Strings.format("book has been borrowed!"), "BOOK_BORROWED");
            }

            book.status = BookStatus.BORROWED;
            book.borrowUserId = request.borrowUserId;
            LocalDateTime now = LocalDateTime.now();
            book.borrowedTime = now;
            book.returnDate = request.returnDate;
            book.updatedTime = now;
            book.updatedBy = request.requestedBy;
            BorrowRecord borrowRecord = buildBorrowRecord(request, book, now);

            bookRepository.update(book);
            borrowRecordMongoCollection.insert(borrowRecord);

        }
    }

    private BorrowRecord buildBorrowRecord(BorrowBookRequest request, Book book, LocalDateTime now) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.id = ObjectId.get();
        borrowRecord.user = new BorrowRecord.User();
        borrowRecord.user.id = request.borrowUserId;
        borrowRecord.user.username = request.borrowUsername;
        borrowRecord.book = new BorrowRecord.Book();
        borrowRecord.book.id = book.id;
        borrowRecord.book.name = book.name;
        borrowRecord.book.description = book.description;
        borrowRecord.book.authors = queryAuthorsByBookId(book.id).stream().map(author -> {
            BorrowRecord.Author view = new BorrowRecord.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());
        borrowRecord.book.categories = queryCategoriesByBookId(book.id).stream().map(category -> {
            BorrowRecord.Category view = new BorrowRecord.Category();
            view.id = category.id;
            view.name = category.name;
            return view;
        }).collect(Collectors.toList());
        borrowRecord.book.tags = queryTagsByBookId(book.id).stream().map(tag -> {
            BorrowRecord.Tag view = new BorrowRecord.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());
        borrowRecord.borrowedTime = now;
        borrowRecord.returnDate = request.returnDate.atStartOfDay();
        borrowRecord.createdTime = now;
        borrowRecord.updatedTime = now;
        borrowRecord.createdBy = request.requestedBy;
        borrowRecord.updatedBy = request.requestedBy;
        return borrowRecord;
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
