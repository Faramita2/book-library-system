package app.api.backoffice.book;

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

    @Property(name = "borrow_username")
    public String borrowUsername;

    @Property(name = "borrowed_time")
    public LocalDateTime borrowedTime;

    @Property(name = "return_date")
    public LocalDate returnDate;
}
