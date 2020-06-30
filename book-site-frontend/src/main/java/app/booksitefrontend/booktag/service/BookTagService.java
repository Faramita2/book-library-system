package app.booksitefrontend.booktag.service;

import app.api.booksitefrontend.booktag.SearchBookTagAJAXRequest;
import app.api.booksitefrontend.booktag.SearchBookTagAJAXResponse;
import app.book.api.TagWebService;
import app.book.api.tag.SearchTagRequest;
import app.book.api.tag.SearchTagResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookTagService {
    @Inject
    TagWebService tagWebService;

    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        SearchTagRequest req = new SearchTagRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.name = request.name;
        SearchTagResponse resp = tagWebService.search(req);
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
}
