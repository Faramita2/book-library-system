package app.booksitefrontend.user.web;

import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import core.framework.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zoo
 */
public class AuthInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        UserPass pass = invocation.annotation(UserPass.class);

        if (pass == null) {
            invocation.context().request().session().get("user_id").orElseThrow(()
                -> new UnauthorizedException("You need login first."));

            logger.info("pass authorize");
        }

        return invocation.proceed();
    }
}
