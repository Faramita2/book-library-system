package app.website.booktag.service;

import app.api.website.booktag.ListBookTagAJAXResponse;
import app.api.website.booktag.SearchBookTagAJAXRequest;
import app.api.website.booktag.SearchBookTagAJAXResponse;
import app.book.api.TagWebService;
import app.book.api.tag.ListTagResponse;
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
        SearchTagRequest searchTagRequest = new SearchTagRequest();
        searchTagRequest.skip = request.skip;
        searchTagRequest.limit = request.limit;
        searchTagRequest.name = request.name;
        SearchTagResponse searchTagResponse = tagWebService.search(searchTagRequest);
        SearchBookTagAJAXResponse response = new SearchBookTagAJAXResponse();
        response.total = searchTagResponse.total;
        response.tags = searchTagResponse.tags.stream().map(tag -> {
            SearchBookTagAJAXResponse.Tag view = new SearchBookTagAJAXResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public ListBookTagAJAXResponse list() {
        ListBookTagAJAXResponse response = new ListBookTagAJAXResponse();
        ListTagResponse listTagResponse = tagWebService.list();
        response.total = listTagResponse.total;
        response.tags = listTagResponse.tags.stream().map(tag -> {
            ListBookTagAJAXResponse.Tag view = new ListBookTagAJAXResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }
}
