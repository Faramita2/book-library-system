package app.backoffice.api;

import app.api.backoffice.UserAJAXWebService;
import app.api.backoffice.user.CreateUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXResponse;
import app.backoffice.service.UserService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.WebContext;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;
    @Inject
    WebContext webContext;
    private final String emailHostName;

    public UserAJAXWebServiceImpl(String emailHostName) {
        this.emailHostName = emailHostName;
    }

    @Override
    public void create(CreateUserAJAXRequest request) {
        service.create(request, adminAccount());
    }

    @Override
    public SearchUserAJAXResponse search(SearchUserAJAXRequest request) {
        return service.search(request);
    }

    @Override
    public void active(Long id) {
        ActionLogContext.put("id", id);
        service.active(id, adminAccount());
    }

    @Override
    public void inactive(Long id) {
        ActionLogContext.put("id", id);
        service.inactive(id, adminAccount());
    }

    @Override
    public void resetPassword(Long id) {
        ActionLogContext.put("id", id);
        service.resetPassword(id, emailHostName);
    }

    private String adminAccount() {
        return webContext.request().session().get("admin_account").orElseThrow(() -> new UnauthorizedException("please login first."));
    }
}
