package app.admin.admin.web;

import app.admin.admin.service.BOAdminService;
import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOCreateAdminRequest;
import app.api.admin.admin.BOLoginAdminRequest;
import app.api.admin.admin.BOSearchAdminRequest;
import app.api.admin.admin.BOSearchAdminResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author zoo
 */
public class BOAdminWebServiceImpl implements BOAdminWebService {
    @Inject
    BOAdminService service;

    @Override
    public void create(BOCreateAdminRequest request) {
        service.create(request);
    }

    @Override
    public BOSearchAdminResponse search(BOSearchAdminRequest request) {
        return service.search(request);
    }

    @Override
    public void login(BOLoginAdminRequest request) {
        ActionLogContext.put("admin_account", request.account);
        service.login(request);
    }
}
