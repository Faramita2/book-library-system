package app.booksite.api;

import app.api.backoffice.UserAJAXWebService;
import app.api.backoffice.user.CreateUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXResponse;
import app.booksite.service.UserService;
import app.booksite.web.interceptor.SkipLogin;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

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
    public void active(Long id) {
        ActionLogContext.put("id", id);
        service.active(id);
    }

    @Override
    public void inactive(Long id) {
        ActionLogContext.put("id", id);
        service.inactive(id);
    }

    @SkipLogin
    @Override
    public void resetPassword(Long id) {
        ActionLogContext.put("id", id);
        service.resetPassword(id);
    }
}
