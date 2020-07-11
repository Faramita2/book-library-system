package app.backoffice.web;

/**
 * @author meow
 */
public class BackOfficeException extends RuntimeException {
    private static final long serialVersionUID = -7034897190745766939L;

    public BackOfficeException(String msg, Throwable e) {
        super(msg, e);
    }
}
