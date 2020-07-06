package app.book.book.domain;

import core.framework.db.DBEnumValue;

/**
 * @author zoo
 */
public enum BookStatus {
    @DBEnumValue("AVAILABLE")
    AVAILABLE,
    @DBEnumValue("BORROWED")
    BORROWED
}
