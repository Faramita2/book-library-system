package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class BOSearchUserResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "users")
    @NotNull
    public List<User> users;

    public static class User {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "username")
        @NotNull
        @NotBlank
        public String username;

        @Property(name = "email")
        @NotNull
        @NotBlank
        public String email;

        @Property(name = "status")
        @NotNull
        public UserStatusView status;

        @Property(name = "created_at")
        @NotNull
        public LocalDateTime createdAt;

        @Property(name = "updated_at")
        @NotNull
        public LocalDateTime updatedAt;
    }
}
