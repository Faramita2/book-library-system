package app.api.authentication.authentication;

/**
 * @author meow
 */
public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 8663360723004690206L;

    public AuthenticationException(String message, Throwable e) {
        super(message, e);
    }
}
