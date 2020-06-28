package app.notification.notification.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
@Table(name = "notifications")
public class Notification {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    public Long id;

    @Column(name = "user_id")
    @NotNull
    public Long userId;

    @Column(name = "content")
    @NotNull
    @NotBlank
    public String content;

    @Column(name = "created_at")
    @NotNull
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    public LocalDateTime updatedAt;

    @Column(name = "created_by")
    @NotNull
    @NotBlank
    public String createdBy;

    @Column(name = "updated_by")
    @NotNull
    @NotBlank
    public String updatedBy;
}
