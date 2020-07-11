package app.website.api;

import app.api.authentication.authentication.LoginResponse;
import app.website.api.user.LoginAJAXRequest;
import app.website.api.user.ResetPasswordAJAXRequest;
import app.website.service.UserService;
import app.website.web.interceptor.SkipLogin;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.CookieSpec;
import core.framework.web.WebContext;

import java.util.Map;

/**
 * @author meow
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService service;
    @Inject
    WebContext webContext;
    @Inject
    Redis redis;

    @SkipLogin
    @Override
    public void login(LoginAJAXRequest request) {
        String sessionId = webContext.request().cookie(new CookieSpec("SessionId")).orElse(null);
        LoginResponse loginResponse = service.login(request, sessionId);

        Map<String, String> session = Map.of(
            "user_id", String.valueOf(loginResponse.id),
            "username", request.username,
            "email", loginResponse.email,
            "user_status", loginResponse.status.name()
        );
        redis.hash().multiSet(Strings.format("session:{}", sessionId), session);
    }

    @SkipLogin
    @Override
    public void logout() {
        String sessionId = webContext.request().cookie(new CookieSpec("SessionId")).orElse(null);
        redis.del(Strings.format("session:{}", sessionId));
    }

    @SkipLogin
    @Override
    public void resetPassword(ResetPasswordAJAXRequest request) {
        service.resetPassword(request);
    }
}
