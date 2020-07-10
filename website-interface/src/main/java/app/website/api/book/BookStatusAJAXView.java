package app.website.api.book;

import core.framework.api.json.Property;

/**
 * @author meow
 */
public enum BookStatusAJAXView {
    @Property(name = "AVAILABLE")
    AVAILABLE,
    @Property(name = "BORROWED")
    BORROWED
}
