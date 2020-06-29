package app.api.backoffice.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoo
 */
public class GetBookAJAXResponse {
    @Property(name = "id")
    @NotNull
    public Long id;

    @Property(name = "name")
    @NotNull
    @NotBlank
    public String name;

    @Property(name = "tag_name")
    @NotNull
    @NotBlank
    public String tagName;

    @Property(name = "description")
    @NotNull
    @NotBlank
    public String description;

    @Property(name = "category_name")
    @NotNull
    @NotBlank
    public String categoryName;

    @Property(name = "author_name")
    @NotNull
    @NotBlank
    public String authorName;

    @Property(name = "status")
    @NotNull
    public BookStatusAJAXView status;

    @Property(name = "borrower_name")
    public String borrowerName;

    @Property(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @Property(name = "return_at")
    public LocalDate returnAt;
}
