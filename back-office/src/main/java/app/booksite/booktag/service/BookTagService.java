package app.booksite.booktag.service;

import app.api.backoffice.booktag.CreateBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXRequest;
import app.api.backoffice.booktag.SearchBookTagAJAXResponse;
import app.api.backoffice.booktag.UpdateBookTagAJAXRequest;
import app.book.api.BOTagWebService;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOSearchTagRequest;
import app.book.api.tag.BOSearchTagResponse;
import app.book.api.tag.BOUpdateTagRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

import java.util.stream.Collectors;

/**
 * @author zoo
 */
public class BookTagService {
    @Inject
    BOTagWebService boTagWebService;
    @Inject
    WebContext webContext;

    public SearchBookTagAJAXResponse search(SearchBookTagAJAXRequest request) {
        BOSearchTagRequest boSearchTagRequest = new BOSearchTagRequest();
        boSearchTagRequest.skip = request.skip;
        boSearchTagRequest.limit = request.limit;
        boSearchTagRequest.name = request.name;
        BOSearchTagResponse boSearchTagResponse = boTagWebService.search(boSearchTagRequest);

        SearchBookTagAJAXResponse response = new SearchBookTagAJAXResponse();
        response.total = boSearchTagResponse.total;
        response.tags = boSearchTagResponse.tags.stream().map(tag -> {
            SearchBookTagAJAXResponse.Tag view = new SearchBookTagAJAXResponse.Tag();
            view.id = tag.id;
            view.name = tag.name;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void create(CreateBookTagAJAXRequest request) {
        BOCreateTagRequest boCreateTagRequest = new BOCreateTagRequest();
        boCreateTagRequest.name = request.name;
        boCreateTagRequest.requestedBy = adminAccount();

        boTagWebService.create(boCreateTagRequest);
    }

    public void update(Long id, UpdateBookTagAJAXRequest request) {
        BOUpdateTagRequest boUpdateTagRequest = new BOUpdateTagRequest();
        boUpdateTagRequest.name = request.name;
        boUpdateTagRequest.requestedBy = adminAccount();

        boTagWebService.update(id, boUpdateTagRequest);
    }

    public void delete(Long id) {
        boTagWebService.delete(id);
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
