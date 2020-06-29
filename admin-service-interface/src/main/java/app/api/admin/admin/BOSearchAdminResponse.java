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
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "admins")
    @NotNull
    public List<Admin> admins;

    public static class Admin {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "account")
        @NotNull
        @NotBlank
        public String account;

        @Property(name = "created_at")
        @NotNull
        public LocalDateTime createdAt;

        @Property(name = "updated_at")
        @NotNull
        public LocalDateTime updatedAt;
    }}
