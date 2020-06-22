package app.book.api.author;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author zoo
 */
public class BOSearchAuthorResponse {
    @Property(name = "total")
    @NotNull
    public Long total;

    @Property(name = "authors")
    public List<Author> authors;

    public static class Author {
        @Property(name = "id")
        @NotNull
        public Long id;

        @Property(name = "name")
        @NotNull
        @NotBlank
        public String name;
    }
}
