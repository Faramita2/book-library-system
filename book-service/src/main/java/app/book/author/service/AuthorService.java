package app.book.author.service;

import app.book.api.author.ListAuthorResponse;
import app.book.api.author.SearchAuthorRequest;
import app.book.api.author.SearchAuthorResponse;
import app.book.author.domain.Author;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;

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
        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        response.total = query.count();
        response.authors = query.fetch().stream().map(author -> {
            SearchAuthorResponse.Author view = new SearchAuthorResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());
        return response;
    }

    public ListAuthorResponse list() {
        ListAuthorResponse response = new ListAuthorResponse();
        Query<Author> query = repository.select();
        response.total = query.count();
        response.authors = query.fetch().stream().map(author -> {
            ListAuthorResponse.Author view = new ListAuthorResponse.Author();
            view.id = author.id;
            view.name = author.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
