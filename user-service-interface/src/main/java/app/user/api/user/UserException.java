package app.user.api.user;

/**
 * @author meow
 */
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 8663360723004690205L;

    public UserException(String message, Throwable e) {
        super(message, e);
    }
}
