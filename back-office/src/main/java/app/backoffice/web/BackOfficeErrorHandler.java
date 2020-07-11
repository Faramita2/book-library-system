package app.backoffice.web;

import core.framework.api.http.HTTPStatus;
import core.framework.web.ErrorHandler;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Optional;

/**
 * @author meow
 */
public class BackOfficeErrorHandler implements ErrorHandler {
    @Override
    public Optional<Response> handle(Request request, Throwable e) {
        if (e instanceof BackOfficeException) {
            String message = e.getMessage();
            return Optional.of(Response.bean(message).status(HTTPStatus.INTERNAL_SERVER_ERROR));
        } else {
            return Optional.empty();
        }
    }
}
