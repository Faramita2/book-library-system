package app.api.backoffice.bookcategory;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class SearchBookCategoryAJAXResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

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
