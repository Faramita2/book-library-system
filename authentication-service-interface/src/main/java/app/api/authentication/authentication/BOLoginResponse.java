package app.api.authentication.authentication;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class BOLoginResponse {
    @NotNull
    @Property(name = "id")
    public Long id;
}
