package app.book.tag.web;

import app.book.api.TagWebService;
import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import app.book.tag.service.TagService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class TagWebServiceImpl implements TagWebService {
    @Inject
    TagService service;

    @Override
    public SearchTagResponse search(SearchTagRequest request) {
        return service.search(request);
    }

    @Override
    public ListTagResponse list() {
        return service.list();
    }
}
