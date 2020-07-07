package app.website.web.interceptor;

import app.api.website.user.UserStatusAJAXView;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import core.framework.web.Session;
import core.framework.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zoo
 */
public class AuthInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Inject
    Redis redis;

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        SkipLogin pass = invocation.annotation(SkipLogin.class);
        if (pass == null) {
            Session session = invocation.context().request().session();
            String userId = session.get("user_id").orElseThrow(() -> new UnauthorizedException("please login first."));
            String isLogin = redis.get(Strings.format("users:{}:login", userId));
            String userStatus = redis.get(Strings.format("users:{}:status", userId));
            if (userStatus == null || !userStatus.equals(UserStatusAJAXView.ACTIVE.name())) {
                throw new UnauthorizedException("Your account is inactive.");
            }
            if (isLogin == null || !isLogin.equals("TRUE")) {
                throw new UnauthorizedException("please login first.");
            }
        }

        logger.info("pass authorize");
        return invocation.proceed();
    }
}
