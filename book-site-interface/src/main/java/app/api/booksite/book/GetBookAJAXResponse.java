package app.api.booksite.book;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zoo
 */
public class GetBookAJAXResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "tag_names")
    public List<String> tagNames;

    @NotNull
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "category_names")
    public List<String> categoryNames;

    @NotNull
    @Property(name = "author_names")
    public List<String> authorNames;

    @NotNull
    @Property(name = "status")
    public BookStatusAJAXView status;

    @Property(name = "borrower_name")
    public String borrowerName;

    // todo name means
    @Property(name = "borrowed_at")
    public LocalDateTime borrowedAt;

    @Property(name = "return_at")
    public LocalDate returnAt;
}
