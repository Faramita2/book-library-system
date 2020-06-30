package app.booksite.user.web;

import app.api.booksite.UserAJAXWebService;
import app.api.booksite.user.CreateUserAJAXRequest;
import app.api.booksite.user.ResetUserPasswordAJAXRequest;
import app.api.booksite.user.SearchUserAJAXRequest;
import app.api.booksite.user.SearchUserAJAXResponse;
import app.api.booksite.user.UpdateUserAJAXRequest;
import app.booksite.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;

    @Override
    public void create(CreateUserAJAXRequest request) {
        service.create(request);
    }

    @Override
    public SearchUserAJAXResponse search(SearchUserAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void update(Long id, UpdateUserAJAXRequest request) {

    }

    @Override
    public void resetPassword(Long id, ResetUserPasswordAJAXRequest request) {

    }
}
