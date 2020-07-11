package app.website.web;

/**
 * @author meow
 */
public class WebsiteError extends RuntimeException {
    private static final long serialVersionUID = -7034897190745766939L;

    public WebsiteError(String msg, Exception e) {
        super(msg, e);
    }
}
