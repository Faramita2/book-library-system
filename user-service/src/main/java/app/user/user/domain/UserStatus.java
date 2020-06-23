package app.user.user.domain;

import core.framework.db.DBEnumValue;

/**
 * @author zoo
 */
public enum UserStatus {
    @DBEnumValue("ACTIVE")
    ACTIVE,
    @DBEnumValue("INACTIVE")
    INACTIVE
}
