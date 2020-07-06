package app.api.backoffice.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class SearchUserAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "users")
    public List<User> users;

    public static class User {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "username")
        public String username;

        @NotNull
        @NotBlank
        @Property(name = "email")
        public String email;

        @NotNull
        @Property(name = "status")
        public UserStatusAJAXView status;

        @NotNull
        @Property(name = "created_time")
        public LocalDateTime createdTime;

        @NotNull
        @Property(name = "updated_time")
        public LocalDateTime updatedTime;
    }
}
