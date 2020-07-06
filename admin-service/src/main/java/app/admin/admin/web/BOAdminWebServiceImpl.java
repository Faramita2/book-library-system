package app.admin.admin.web;

import app.admin.admin.service.BOAdminService;
import app.api.admin.BOAdminWebService;
import app.api.admin.admin.BOGetAdminByAccountRequest;
import app.api.admin.admin.BOGetAdminByAccountResponse;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOAdminWebServiceImpl implements BOAdminWebService {
    @Inject
    BOAdminService service;

    @Override
    public BOGetAdminByAccountResponse getByAccount(BOGetAdminByAccountRequest request) {
        return service.getByAccount(request);
    }
}
