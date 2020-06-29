package app.backoffice.admin.web;

import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.Session;
import core.framework.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author zoo
 */
public class AuthInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        Pass pass = invocation.annotation(Pass.class);
        Request request = invocation.context().request();
        Session session = request.session();
        if (pass == null) {
            session.get("admin").orElseThrow(() -> new UnauthorizedException("You need login first."));
        } else {
            Optional<String> adminId = session.get("admin_id");
            Optional<String> adminAccount = session.get("admin_account");

            if (adminId.isPresent() && adminAccount.isPresent()) {
                logger.info("already login admin, id = {}, account = {}", adminId.get(), adminAccount.get());
            } else {
                logger.info("pass authorize");
            }
        }

        return invocation.proceed();
    }
}
