package app.user.user.exception;

import core.framework.log.ErrorCode;

/**
 * @author meow
 */
public class UserException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = 8663360723004690205L;
    private final String errorCode;

    public UserException(String message, Throwable e, String errorCode) {
        super(message, e);
        this.errorCode = errorCode;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }
}
