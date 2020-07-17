package app.authentication.authentication.exception;

import core.framework.log.ErrorCode;

/**
 * @author meow
 */
public class AuthenticationException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = 8663360723004690206L;

    private final String errorCode;


    public AuthenticationException(String message, Throwable e, String errorCode) {
        super(message, e);
        this.errorCode = errorCode;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }
}
