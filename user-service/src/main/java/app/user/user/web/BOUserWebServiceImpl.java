package app.user.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.user.service.BOUserService;
import core.framework.inject.Inject;

/**
 * @author zoo
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    BOUserService service;

    @Override
    public void create(BOCreateUserRequest request) {
        service.create(request);
    }
}
