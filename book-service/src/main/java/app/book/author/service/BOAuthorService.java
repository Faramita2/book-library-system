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
        author.createdAt = LocalDateTime.now();
        author.updatedAt = LocalDateTime.now();
        author.createdBy = "BookService";
        author.updatedBy = "BookService";

        repository.insert(author).orElseThrow();
    }

    public BOSearchAuthorResponse search(BOSearchAuthorRequest request) {
        Query<Author> query = repository.select();

        if (request.name != null) {
            query.where("name like ?%", request.name);
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchAuthorResponse response = new BOSearchAuthorResponse();
        response.total = query.count();
        response.authors = query.fetch().parallelStream().map(a -> {
            BOSearchAuthorResponse.Author author = new BOSearchAuthorResponse.Author();
            author.id = a.id;
            author.name = a.name;

            return author;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateAuthorRequest request) {
        Author author = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("author not found, id = {}", id)));
        author.name = request.name;
        repository.partialUpdate(author);
    }

    public void delete(Long id) {
        repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("author not found, id = {}", id)));
        repository.delete(id);
    }
}
