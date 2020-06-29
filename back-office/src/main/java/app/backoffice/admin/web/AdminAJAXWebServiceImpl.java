package app.backoffice.admin.web;

import app.api.backoffice.AdminAJAXWebService;
import app.api.backoffice.admin.LoginAdminAJAXRequest;
import app.backoffice.admin.service.AdminService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class AdminAJAXWebServiceImpl implements AdminAJAXWebService {
    @Inject
    AdminService service;

    @Pass
    @Override
    public void login(LoginAdminAJAXRequest request) {
        ActionLogContext.put("admin_account", request.account);
        service.login(request);
    }
}
