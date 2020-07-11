package app.book.author.service;

import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import app.book.author.domain.Author;
import app.book.book.domain.BookAuthor;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOAuthorService {
    @Inject
    Repository<Author> authorRepository;
    @Inject
    Repository<BookAuthor> bookAuthorRepository;

    public void create(BOCreateAuthorRequest request) {
        Author author = new Author();
        author.name = request.name;
        author.createdTime = LocalDateTime.now();
        author.updatedTime = LocalDateTime.now();
        author.createdBy = request.requestedBy;
        author.updatedBy = request.requestedBy;

        authorRepository.insert(author);
    }

    public BOSearchAuthorResponse search(BOSearchAuthorRequest request) {
        Query<Author> query = authorRepository.select();
        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("%{}%", request.name));
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchAuthorResponse response = new BOSearchAuthorResponse();
        response.total = query.count();
        response.authors = query.fetch().stream().map(author -> {
            BOSearchAuthorResponse.Author view = new BOSearchAuthorResponse.Author();
            view.id = author.id;
            view.name = author.name;

            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateAuthorRequest request) {
        Author author = authorRepository.get(id).orElseThrow(() -> new NotFoundException(
            Strings.format("author not found, id = {}", id), "BOOK_AUTHOR_NOT_FOUND"));
        author.name = request.name;
        author.updatedBy = request.requestedBy;
        author.updatedTime = LocalDateTime.now();

        authorRepository.partialUpdate(author);
    }

    public void delete(Long id) {
        authorRepository.get(id).orElseThrow(() -> new NotFoundException(
            Strings.format("author not found, id = {}", id), "BOOK_AUTHOR_NOT_FOUND"));
        if (bookAuthorRepository.count("author_id = ?", id) != 0) {
            throw new BadRequestException("books have this author, cannot delete it!", "BOOK_RELATED_AUTHOR");
        }
        authorRepository.delete(id);
    }
}
