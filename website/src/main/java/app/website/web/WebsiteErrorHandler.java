package app.website.web;

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
public class WebsiteErrorHandler implements ErrorHandler {
    @Override
    public Optional<Response> handle(Request request, Throwable e) {
        if (e instanceof AuthenticationException || e instanceof UserException) {
            return Optional.of(Response.bean(e.getMessage()).status(HTTPStatus.INTERNAL_SERVER_ERROR));
        }
        return Optional.empty();
    }
}
