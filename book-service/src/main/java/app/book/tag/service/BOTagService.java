package app.book.tag.service;

import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
import app.book.tag.domain.Tag;
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
public class BOTagService {
    @Inject
    Repository<Tag> repository;

    public void create(BOCreateTagRequest request) {
        Tag tag = new Tag();
        tag.name = request.name;
        tag.createdAt = LocalDateTime.now();
        tag.updatedAt = LocalDateTime.now();
        //TODO
        tag.createdBy = "BookService";
        tag.updatedBy = "BookService";

        repository.insert(tag).orElseThrow();
    }

    public BOSearchTagResponse search(BOSearchTagRequest request) {
        Query<Tag> query = repository.select();

        if (request.name != null) {
            query.where("name like ?", request.name + "%");
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchTagResponse response = new BOSearchTagResponse();
        response.total = query.count();
        response.tags = query.fetch().parallelStream().map(a -> {
            BOSearchTagResponse.Tag tag = new BOSearchTagResponse.Tag();
            tag.id = a.id;
            tag.name = a.name;

            return tag;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateTagRequest request) {
        Tag tag = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("tag not found, id = {}", id)));
        tag.name = request.name;
        repository.partialUpdate(tag);
    }

    public void delete(Long id) {
        repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("tag not found, id = {}", id)));
        repository.delete(id);
    }
}
