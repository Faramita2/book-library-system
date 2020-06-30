package app.backoffice.booktag.service;

import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.book.api.BOTagWebService;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
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
        if (resp.tags != null){
            response.tags = resp.tags.stream().map(tag -> {
                SearchBookTagAJAXResponse.Tag t = new SearchBookTagAJAXResponse.Tag();
                t.id = tag.id;
                t.name = tag.name;

                return t;
            }).collect(Collectors.toList());
        }

        return response;
    }
}