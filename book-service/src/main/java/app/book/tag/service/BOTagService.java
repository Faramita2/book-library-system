package app.book.tag.service;

import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
import app.book.book.domain.BookTag;
import app.book.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.log.Markers;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BOTagService {
    @Inject
    Repository<Tag> tagRepository;
    @Inject
    Repository<BookTag> bookTagRepository;

    public void create(BOCreateTagRequest request) {
        tagRepository.selectOne("name = ?", request.name).ifPresent(tag -> {
            throw new ConflictException(Strings.format("tag exists, name = {}", tag.name), Markers.errorCode("BOOK_TAG_EXISTS").getName());
        });
        Tag tag = new Tag();
        tag.name = request.name;
        LocalDateTime now = LocalDateTime.now();
        tag.createdTime = now;
        tag.updatedTime = now;
        tag.createdBy = request.requestedBy;
        tag.updatedBy = request.requestedBy;

        tagRepository.insert(tag);
    }

    public BOSearchTagResponse search(BOSearchTagRequest request) {
        Query<Tag> query = tagRepository.select();

        if (!Strings.isBlank(request.name)) {
            query.where("name LIKE ?", Strings.format("%{}%", request.name));
        }

        query.skip(request.skip);
        query.limit(request.limit);

        BOSearchTagResponse response = new BOSearchTagResponse();
        response.total = query.count();
        response.tags = query.fetch().stream().map(tag -> {
            BOSearchTagResponse.Tag view = new BOSearchTagResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, BOUpdateTagRequest request) {
        Tag tag = tagRepository.get(id).orElseThrow(() -> new NotFoundException(
            Strings.format("tag not found, id = {}", id), Markers.errorCode("BOOK_TAG_NOT_FOUND").getName()));
        tag.name = request.name;
        tag.updatedBy = request.requestedBy;
        tag.updatedTime = LocalDateTime.now();

        tagRepository.partialUpdate(tag);
    }

    public void delete(Long id) {
        tagRepository.get(id).orElseThrow(() -> new NotFoundException(
            Strings.format("tag not found, id = {}", id), Markers.errorCode("BOOK_TAG_NOT_FOUND").getName()));
        if (bookTagRepository.count("tag_id = ?", id) != 0) {
            throw new BadRequestException("books have this tag, cannot delete it!", Markers.errorCode("BOOK_RELATED_TAG").getName());
        }
        tagRepository.delete(id);
    }
}
