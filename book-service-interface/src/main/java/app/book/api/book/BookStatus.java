package app.book.api.book;

import core.framework.api.json.Property;

/**
 * @author zoo
 */
public enum BookStatus {
    @Property(name = "NORMAL")
    NORMAL,
    @Property(name = "BORROWED")
    BORROWED
}
