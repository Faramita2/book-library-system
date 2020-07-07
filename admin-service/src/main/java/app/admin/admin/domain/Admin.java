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
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "account")
    public String account;

    @NotNull
    @NotBlank
    @Column(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Column(name = "salt")
    public String salt;

    @NotNull
    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @Column(name = "updated_time")
    public LocalDateTime updatedTime;

    @NotNull
    @NotBlank
    @Column(name = "created_by")
    public String createdBy;

    @NotNull
    @NotBlank
    @Column(name = "updated_by")
    public String updatedBy;
}
