package app.booksite.user.service;

import app.api.booksite.user.CreateUserAJAXRequest;
import app.api.booksite.user.ResetUserPasswordAJAXRequest;
import app.api.booksite.user.SearchUserAJAXRequest;
import app.api.booksite.user.SearchUserAJAXResponse;
import app.api.booksite.user.UpdateUserAJAXRequest;
import app.api.booksite.user.UserStatusAJAXView;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOResetUserPasswordRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

import java.util.stream.Collectors;

/**
 * @author meow
 */
public class UserService {
    @Inject
    BOUserWebService userWebService;
    @Inject
    WebContext webContext;

    public void create(CreateUserAJAXRequest request) {
        BOCreateUserRequest req = new BOCreateUserRequest();
        req.email = request.email;
        req.username = request.username;
        req.password = request.password;
        req.status = UserStatusView.valueOf(request.status.name());
        req.operator = "book-site";
        userWebService.create(req);
    }

    public SearchUserAJAXResponse search(SearchUserAJAXRequest request) {
        BOSearchUserRequest req = new BOSearchUserRequest();
        req.skip = request.skip;
        req.limit = request.limit;
        req.email = request.email;
        req.username = request.username;
        req.ids = request.ids;
        req.status = request.status != null ? UserStatusView.valueOf(request.status.name()) : null;
        BOSearchUserResponse resp = userWebService.search(req);

        SearchUserAJAXResponse response = new SearchUserAJAXResponse();
        response.total = resp.total;
        response.users = resp.users.stream().map(user -> {
            SearchUserAJAXResponse.User view = new SearchUserAJAXResponse.User();
            view.id = user.id;
            view.email = user.email;
            view.username = user.username;
            view.status = UserStatusAJAXView.valueOf(user.status.name());
            view.createdAt = user.createdAt;
            view.updatedAt = user.updatedAt;
            return view;
        }).collect(Collectors.toList());

        return response;
    }

    public void update(Long id, UpdateUserAJAXRequest request) {
        BOUpdateUserRequest req = new BOUpdateUserRequest();
        if (request.status != null) {
            req.status = UserStatusView.valueOf(request.status.name());
        }
        req.operator = "book-site";
        userWebService.update(id, req);
        if (request.status != null) {
            webContext.request().session().set("user_status", request.status.name());
        }
    }

    public void resetPassword(Long id, ResetUserPasswordAJAXRequest request) {
        BOResetUserPasswordRequest req = new BOResetUserPasswordRequest();
        req.password = request.password;
        req.passwordConfirm = request.passwordConfirm;
        req.operator = "book-site";
        userWebService.resetPassword(id, req);
    }
}
