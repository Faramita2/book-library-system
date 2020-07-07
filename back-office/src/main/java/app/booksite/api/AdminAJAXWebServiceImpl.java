package app.booksite.api;

import app.api.authentication.authentication.BOLoginResponse;
import app.api.backoffice.AdminAJAXWebService;
import app.api.backoffice.admin.LoginAJAXRequest;
import app.booksite.service.AdminService;
import app.booksite.web.interceptor.SkipLogin;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;

/**
 * @author zoo
 */
public class AdminAJAXWebServiceImpl implements AdminAJAXWebService {
    @Inject
    AdminService service;
    @Inject
    WebContext webContext;

    @SkipLogin
    @Override
    public void login(LoginAJAXRequest request) {
        ActionLogContext.put("account", request.account);
        BOLoginResponse boLoginResponse = service.login(request);
        webContext.request().session().set("admin_id", String.valueOf(boLoginResponse.id));
        webContext.request().session().set("admin_account", request.account);
    }

    @Override
    public void logout() {
        webContext.request().session().invalidate();
    }
}
