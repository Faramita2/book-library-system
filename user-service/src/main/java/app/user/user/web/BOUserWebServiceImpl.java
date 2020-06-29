package app.user.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOResetUserPasswordRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
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

    @Override
    public BOSearchUserResponse search(BOSearchUserRequest request) {
        return service.search(request);
    }

    @Override
    public void update(Long id, BOUpdateUserRequest request) {
        service.update(id, request);
    }

    @Override
    public void resetPassword(Long id, BOResetUserPasswordRequest request) {
        service.resetPassword(id, request);
    }
}
