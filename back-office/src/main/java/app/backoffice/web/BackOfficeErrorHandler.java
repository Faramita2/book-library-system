package app.backoffice.web;

import app.api.authentication.authentication.AuthenticationException;
import app.user.api.user.UserException;
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
        if (e instanceof UserException || e instanceof AuthenticationException) {
            String message = e.getMessage();
            return Optional.of(Response.bean(message).status(HTTPStatus.INTERNAL_SERVER_ERROR));
        } else {
            return Optional.empty();
        }
    }
}
