package app.api.admin.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class BOSearchAdminResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "admins")
    public List<Admin> admins;

    public static class Admin {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "account")
        public String account;

        @NotNull
        @Property(name = "created_time")
        public LocalDateTime createdTime;

        @NotNull
        @Property(name = "updated_time")
        public LocalDateTime updatedTime;
    }
}
