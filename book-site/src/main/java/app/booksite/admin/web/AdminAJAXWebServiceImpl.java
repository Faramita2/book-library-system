package app.booksite.admin.web;

import app.api.booksite.AdminAJAXWebService;
import app.api.booksite.admin.LoginAdminAJAXRequest;
import app.booksite.admin.service.AdminService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class AdminAJAXWebServiceImpl implements AdminAJAXWebService {
    @Inject
    AdminService service;

    @AdminPass
    @Override
    public void login(LoginAdminAJAXRequest request) {
        ActionLogContext.put("account", request.account);
        service.login(request);
    }
}
