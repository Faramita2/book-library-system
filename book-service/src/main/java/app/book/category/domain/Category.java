package app.book.category.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author zoo
 */
@Table(name = "categories")
public class Category {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    public Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    public String name;

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
