package app.book;

import app.book.api.BOTagWebService;
import app.book.api.TagWebService;
import app.book.tag.domain.Tag;
import app.book.tag.service.BOTagService;
import app.book.tag.service.TagService;
import app.book.tag.web.BOTagWebServiceImpl;
import app.book.tag.web.TagWebServiceImpl;
import core.framework.module.Module;

/**
 * @author zoo
 */
public class TagModule extends Module {
    @Override
    protected void initialize() {
        dbs();

        services();

        apiServices();
    }

    private void apiServices() {
        api().service(BOTagWebService.class, bind(BOTagWebServiceImpl.class));
        api().service(TagWebService.class, bind(TagWebServiceImpl.class));
    }

    private void services() {
        bind(BOTagService.class);
        bind(TagService.class);
    }

    private void dbs() {
        db().repository(Tag.class);
    }
}
