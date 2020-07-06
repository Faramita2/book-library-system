package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author zoo
 */
public class GetUserByUsernameRequest {
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Property(name = "username")
    public String username;
}
