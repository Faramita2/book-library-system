package app.api.backoffice.book;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public enum BookStatusAJAXView {
    @Property(name = "AVAILABLE")
    AVAILABLE,
    @Property(name = "BORROWED")
    BORROWED
}
