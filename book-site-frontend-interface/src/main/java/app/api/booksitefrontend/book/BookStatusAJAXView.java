package app.api.booksitefrontend.book;

import core.framework.api.json.Property;

/**
 * @author meow
 */
public enum BookStatusAJAXView {
    @Property(name = "NORMAL")
    NORMAL,
    @Property(name = "BORROWED")
    BORROWED
}
