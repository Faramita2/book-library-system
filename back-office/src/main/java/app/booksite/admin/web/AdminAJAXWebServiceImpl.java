package app.booksite.admin.web;

import app.api.backoffice.AdminAJAXWebService;
import app.api.backoffice.admin.LoginAdminAJAXRequest;
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
