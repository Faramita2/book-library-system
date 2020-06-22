package app.book.author.service;

import app.book.api.author.SearchAuthorRequest;
import app.book.api.author.SearchAuthorResponse;
import app.book.author.domain.Author;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class AuthorService {
    @Inject
    Repository<Author> repository;

    public SearchAuthorResponse search(SearchAuthorRequest request) {
        SearchAuthorResponse response = new SearchAuthorResponse();

        Query<Author> query = repository.select();
        if (request.name != null) {
            query.where("`name` like ?", request.name + "%");
        }

        response.total = query.count();
        response.authors = query.fetch().parallelStream().map(a -> {
            SearchAuthorResponse.Author author = new SearchAuthorResponse.Author();
            author.id = a.id;
            author.name = a.name;

            return author;
        }).collect(Collectors.toList());

        return response;
    }
}
