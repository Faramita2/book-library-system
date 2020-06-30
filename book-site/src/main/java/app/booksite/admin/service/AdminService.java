package app.booksite.admin.service;

import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOLoginAdminRequest;
import app.api.admin.admin.BOLoginAdminResponse;
import app.api.booksite.admin.LoginAdminAJAXRequest;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

/**
 * @author zoo
 */
public class AdminService {
    @Inject
    BOAdminWebService boAdminWebService;
    @Inject
    WebContext webContext;

    public void login(LoginAdminAJAXRequest request) {
        BOLoginAdminRequest req = new BOLoginAdminRequest();
        req.account = request.account;
        req.password = request.password;
        BOLoginAdminResponse response = boAdminWebService.login(req);
        webContext.request().session().set("admin_id", String.valueOf(response.id));
        webContext.request().session().set("admin_account", response.account);
    }
}
