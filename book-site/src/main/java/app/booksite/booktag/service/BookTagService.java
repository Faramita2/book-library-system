package app.booksite.booktag.service;

import app.api.booksite.booktag.CreateBookTagAJAXRequest;
import app.api.booksite.booktag.SearchBookTagAJAXRequest;
import app.api.booksite.booktag.SearchBookTagAJAXResponse;
import app.api.booksite.booktag.UpdateBookTagAJAXRequest;
import app.book.api.BOTagWebService;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookTagService {
    @Inject
    BOTagWebService boTagWebService;

    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        BOSearchTagRequest req = new BOSearchTagRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        BOSearchTagResponse resp = boTagWebService.search(req);
        SearchBookTagAJAXResponse response = new SearchBookTagAJAXResponse();
        response.total = resp.total;
        response.tags = resp.tags.stream().map(tag -> {
            SearchBookTagAJAXResponse.Tag view = new SearchBookTagAJAXResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;

            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookTagAJAXRequest request) {
        BOCreateTagRequest req = new BOCreateTagRequest();
        req.name = request.name;
        boTagWebService.create(req);
    }

    public void update(Long id, UpdateBookTagAJAXRequest request) {
        BOUpdateTagRequest req = new BOUpdateTagRequest();
        req.name = request.name;
        boTagWebService.update(id, req);
    }
}
