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

    @NotNull
    @Column(name = "user_id")
    public Long userId;

    @NotNull
    @NotBlank
    @Column(name = "content")
    public String content;

    @NotNull
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @NotNull
    @NotBlank
    @Column(name = "created_by")
    public String createdBy;

    @NotNull
    @NotBlank
    @Column(name = "updated_by")
    public String updatedBy;
}
