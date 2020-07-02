package app.book.tag.service;

import app.book.api.tag.ListTagResponse;
import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import app.book.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class TagService {
    @Inject
    Repository<Tag> repository;

    public SearchTagResponse search(SearchTagRequest request) {
        SearchTagResponse response = new SearchTagResponse();

        Query<Tag> query = repository.select();
        if (request.name != null) {
            query.where("name LIKE ?", Strings.format("{}%", request.name));
        }

        response.total = query.count();
        response.tags = query.fetch().stream().map(tag -> {
            SearchTagResponse.Tag view = new SearchTagResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public ListTagResponse list() {
        ListTagResponse response = new ListTagResponse();
        Query<Tag> query = repository.select();
        response.total = query.count();
        response.tags = query.fetch().stream().map(tag -> {
            ListTagResponse.Tag view = new ListTagResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
