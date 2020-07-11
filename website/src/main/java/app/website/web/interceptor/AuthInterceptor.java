package app.website.web.interceptor;

import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.Strings;
import core.framework.web.CookieSpec;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import core.framework.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
            String sessionId = invocation.context().request().cookie(new CookieSpec("SessionId")).orElse(null);
            Map<String, String> session = redis.hash().getAll(Strings.format("session:{}", sessionId));
            logger.info(session.toString());
            String userId = session.get("user_id");
            if (userId == null) {
                throw new UnauthorizedException("Please login first.");
            }
            String status = redis.get(Strings.format("users:{}:status", userId));
            if (status == null || !status.equals(UserStatusView.ACTIVE.name())) {
                throw new UnauthorizedException("Your account is inactive.");
            }
        }

        logger.info("pass authorize");
        return invocation.proceed();
    }
}
