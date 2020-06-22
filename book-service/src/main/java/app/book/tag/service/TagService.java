package app.book.tag.service;

import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import app.book.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

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
            query.where("`name` like ?", request.name + "%");
        }

        response.total = query.count();
        response.tags = query.fetch().parallelStream().map(a -> {
            SearchTagResponse.Tag tag = new SearchTagResponse.Tag();
            tag.id = a.id;
            tag.name = a.name;

            return tag;
        }).collect(Collectors.toList());

        return response;
    }
}
