package app.book.book.domain;

import core.framework.db.DBEnumValue;

/**
 * @author zoo
 */
public enum BookStatus {
    @DBEnumValue("NORMAL")
    NORMAL,
    @DBEnumValue("BORROWED")
    BORROWED
}
