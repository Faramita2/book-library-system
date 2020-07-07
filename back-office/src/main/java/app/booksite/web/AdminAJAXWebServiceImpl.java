package app.booksite.web;

import app.api.backoffice.AdminAJAXWebService;
import app.api.backoffice.admin.LoginAJAXRequest;
import app.booksite.admin.service.AdminService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class AdminAJAXWebServiceImpl implements AdminAJAXWebService {
    @Inject
    AdminService service;

    @SkipLogin
    @Override
    public void login(LoginAJAXRequest request) {
        ActionLogContext.put("account", request.account);
        service.login(request);
    }

    @Override
    public void logout() {
        service.logout();
    }
}
