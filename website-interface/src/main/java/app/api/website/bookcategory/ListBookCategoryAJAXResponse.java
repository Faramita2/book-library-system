package app.api.website.bookcategory;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author meow
 */
public class ListBookCategoryAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "categories")
    public List<Category> categories;

    public static class Category {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "name")
        @NotNull
        @NotBlank
        public String name;
    }
}
