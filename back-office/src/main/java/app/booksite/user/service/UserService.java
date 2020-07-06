package app.booksite.user.service;

import app.api.backoffice.user.CreateUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXRequest;
import app.api.backoffice.user.SearchUserAJAXResponse;
import app.api.backoffice.user.UpdateUserAJAXRequest;
import app.api.backoffice.user.UserStatusAJAXView;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Randoms;
import core.framework.util.Strings;
import core.framework.web.WebContext;
import core.framework.web.exception.TooManyRequestsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * @author meow
 */
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    BOUserWebService userWebService;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

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
            view.createdTime = user.createdTime;
            view.updatedTime = user.updatedTime;
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

    public void resetPassword(Long id) {
        String userRequestTimesKey = Strings.format("user:{}:resetPassword", id);
        String currentRequestTimes = redis.get(userRequestTimesKey);

        if (Strings.isBlank(currentRequestTimes)) {
            redis.set(userRequestTimesKey, "1", Duration.ofHours(1));
        } else if (Long.parseLong(currentRequestTimes) == 5) {
            throw new TooManyRequestsException("too many request for resetting password");
        } else {
            redis.increaseBy(userRequestTimesKey, 1);
        }

        String token = Randoms.alphaNumeric(32);
        redis.set(token, String.valueOf(id), Duration.ofMinutes(30));

        String hostName = webContext.request().hostName();
        logger.info("send email to user(id = {}) with reset password url: {}/user/reset-password?token={}?requested_by=email", id, hostName, token);
    }
}
