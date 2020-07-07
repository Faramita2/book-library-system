package app.book.author.service;

import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOSearchAuthorRequest;
import app.book.api.author.BOSearchAuthorResponse;
import app.book.api.author.BOUpdateAuthorRequest;
import app.book.author.domain.Author;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOAuthorService {
    @Inject
    Repository<Author> repository;

    public void create(BOCreateAuthorRequest request) {
        Author author = new Author();
        author.name = request.name;
        author.createdTime = LocalDateTime.now();
        author.updatedTime = LocalDateTime.now();
        author.createdBy = request.requestedBy;
        author.updatedBy = request.requestedBy;

        repository.insert(author);
    }

    public BOSearchAuthorResponse search(BOSearchAuthorRequest request) {
        Query<Author> query = repository.select();
        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
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
        Author author = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("author not found, id = {}", id), "BOOK_AUTHOR_NOT_FOUND"));
        author.name = request.name;
        author.updatedBy = request.requestedBy;
        author.updatedTime = LocalDateTime.now();

        repository.partialUpdate(author);
    }

    public void delete(Long id) {
        repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("author not found, id = {}", id), "BOOK_AUTHOR_NOT_FOUND"));
        repository.delete(id);
    }
}
