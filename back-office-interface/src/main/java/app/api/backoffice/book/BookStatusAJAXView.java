package app.api.backoffice.book;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public enum BookStatusAJAXView {
    @Property(name = "NORMAL")
    NORMAL,
    @Property(name = "BORROWED")
    BORROWED
}
