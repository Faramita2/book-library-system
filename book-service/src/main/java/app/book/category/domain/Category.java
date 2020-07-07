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
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "name")
    public String name;

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
