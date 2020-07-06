package app.admin.admin.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
@Table(name = "admins")
public class Admin {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    public Long id;

    @Column(name = "account")
    @NotNull
    @NotBlank
    public String account;

    @Column(name = "password")
    @NotNull
    @NotBlank
    public String password;

    @Column(name = "salt")
    @NotNull
    @NotBlank
    public String salt;

    @Column(name = "created_time")
    @NotNull
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    @NotNull
    public LocalDateTime updatedTime;

    @Column(name = "created_by")
    @NotNull
    @NotBlank
    public String createdBy;

    @Column(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
