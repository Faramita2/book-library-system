package app.book.author.service;

import app.book.api.author.BOCreateAuthorRequest;
import app.book.author.domain.Author;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.time.LocalDateTime;

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
}
