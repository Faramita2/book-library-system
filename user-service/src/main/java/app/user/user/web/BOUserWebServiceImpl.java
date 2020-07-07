package app.user.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.ResetUserPasswordRequest;
import app.user.user.service.BOUserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

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

    @Override
    public BOGetUserResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public BOSearchUserResponse search(BOSearchUserRequest request) {
        return service.search(request);
    }

    @Override
    public void update(Long id, BOUpdateUserRequest request) {
        ActionLogContext.put("id", id);
        service.update(id, request);
    }

    @Override
    public void resetPassword(Long id, ResetUserPasswordRequest request) {
        ActionLogContext.put("id", id);
        service.resetPassword(id, request);
    }
}
