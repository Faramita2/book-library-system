package app.booksite.user.service;

import app.api.backoffice.user.CreateUserAJAXRequest;
import app.api.backoffice.user.ResetUserPasswordAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXResponse;
import app.api.backoffice.user.UpdateUserAJAXRequest;
import app.api.backoffice.user.UserStatusAJAXView;
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
        BOCreateUserRequest boCreateUserRequest = new BOCreateUserRequest();
        boCreateUserRequest.email = request.email;
        boCreateUserRequest.username = request.username;
        boCreateUserRequest.password = request.password;
        boCreateUserRequest.status = UserStatusView.valueOf(request.status.name());
        boCreateUserRequest.operator = "book-site";
        userWebService.create(boCreateUserRequest);
    }

    public SearchUserAJAXResponse search(SearchUserAJAXRequest request) {
        BOSearchUserRequest boSearchUserRequest = new BOSearchUserRequest();
        boSearchUserRequest.skip = request.skip;
        boSearchUserRequest.limit = request.limit;
        boSearchUserRequest.email = request.email;
        boSearchUserRequest.username = request.username;
        boSearchUserRequest.ids = request.ids;
        boSearchUserRequest.status = request.status != null ? UserStatusView.valueOf(request.status.name()) : null;
        BOSearchUserResponse boSearchUserResponse = userWebService.search(boSearchUserRequest);

        SearchUserAJAXResponse response = new SearchUserAJAXResponse();
        response.total = boSearchUserResponse.total;
        response.users = boSearchUserResponse.users.stream().map(user -> {
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
        BOUpdateUserRequest boUpdateUserRequest = new BOUpdateUserRequest();
        if (request.status != null) {
            boUpdateUserRequest.status = UserStatusView.valueOf(request.status.name());
        }
        boUpdateUserRequest.operator = "book-site";
        userWebService.update(id, boUpdateUserRequest);
        if (request.status != null) {
            webContext.request().session().set("user_status", request.status.name());
        }
    }

    public void resetPassword(Long id, ResetUserPasswordAJAXRequest request) {
        BOResetUserPasswordRequest boResetUserPasswordRequest = new BOResetUserPasswordRequest();
        boResetUserPasswordRequest.password = request.password;
        boResetUserPasswordRequest.passwordConfirm = request.passwordConfirm;
        boResetUserPasswordRequest.operator = "book-site";
        userWebService.resetPassword(id, boResetUserPasswordRequest);
    }
}
